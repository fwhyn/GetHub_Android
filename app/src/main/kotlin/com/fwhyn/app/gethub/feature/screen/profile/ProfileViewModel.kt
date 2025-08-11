package com.fwhyn.app.gethub.feature.screen.profile

import androidx.lifecycle.viewModelScope
import com.fwhyn.app.gethub.common.helper.StatusExt
import com.fwhyn.app.gethub.common.helper.emitEvent
import com.fwhyn.app.gethub.feature.func.event.data.model.GetGitHubEventsRepoParam
import com.fwhyn.app.gethub.feature.func.event.data.repository.GetGitHubEventsRepository
import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubReposRepoParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUserProfileRepoParam
import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubReposRepository
import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubUserProfileRepository
import com.fwhyn.app.gethub.feature.screen.profile.component.ProfileMessageCode
import com.fwhyn.app.gethub.feature.screen.profile.helper.extension.toUi
import com.fwhyn.app.gethub.feature.screen.profile.model.GitHubEventUi
import com.fwhyn.app.gethub.feature.screen.profile.model.GitHubRepoUi
import com.fwhyn.app.gethub.feature.screen.profile.model.GitHubUserProfileUi
import com.fwhyn.app.gethub.feature.screen.profile.model.ProfileEvent
import com.fwhyn.app.gethub.feature.screen.profile.model.ProfileProperties
import com.fwhyn.app.gethub.feature.screen.profile.model.ProfileState
import com.fwhyn.lib.baze.common.model.Exzeption
import com.fwhyn.lib.baze.common.model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getGitHubUserProfile: GetGitHubUserProfileRepository,
    private val getGitHubRepos: GetGitHubReposRepository,
    private val getGitHubEvents: GetGitHubEventsRepository,
) : ProfileVmInterface() {

    private val scope: CoroutineScope
        get() = viewModelScope

    private val event = MutableSharedFlow<ProfileEvent>()
    private val state = MutableStateFlow<ProfileState>(ProfileState.Idle)
    private val userName = MutableStateFlow<String>("")
    private val gitHubUserProfile = MutableStateFlow(GitHubUserProfileUi.default())
    private val gitHubRepos = MutableStateFlow<List<GitHubRepoUi>>(emptyList())
    private val gitHubEvents = MutableStateFlow<List<GitHubEventUi>>(emptyList())

    // ----------------------------------------------------------------
    override val properties: ProfileProperties = ProfileProperties(
        event = event,
        state = state,
        gitHubUserProfile = gitHubUserProfile,
        gitHubRepos = gitHubRepos,
        gitHubEvents = gitHubEvents,
    )

    // ----------------------------------------------------------------
    init {
        loadGitHubUserProfile()
    }

    // ----------------------------------------------------------------
    override fun onUpdateUserName(data: String) {
        if (data.isBlank()) {
            event.emitEvent(scope, ProfileEvent.Notify(ProfileMessageCode.DataNotFound))
            return
        }

        userName.value = data
    }

    override fun onLoadNextRepos() {
        if (isAllReposLoaded() && gitHubRepos.value.isNotEmpty()) return

        getGitHubRepos(
            onStart = { state.value = ProfileState.Loading },
            onFinish = { state.value = ProfileState.Idle }
        )
    }

    override fun onLoadNextEvents() {
        if (isAllEventsLoaded()) return

        getGitHubEvents(
            onStart = { state.value = ProfileState.Loading },
            onFinish = { state.value = ProfileState.Idle }
        )
    }

    // ----------------------------------------------------------------
    private fun loadGitHubUserProfile() {
        getGitHubUserProfile.invoke(
            scope = scope,
            onStart = { state.value = ProfileState.Loading },
            onFetchParam = {
                GetGitHubUserProfileRepoParam(username = userName.value)
            },
            onOmitResult = {
                it.onSuccess { data ->
                    gitHubUserProfile.value = data.toUi()

                    getGitHubRepos(onFinish = {
                        getGitHubEvents(onFinish = {
                            state.value = ProfileState.Idle
                        })
                    })
                }.onFailure { error ->
                    handleError(error)
                    state.value = ProfileState.Idle
                }
            },
        )
    }

    fun getGitHubRepos(
        onStart: () -> Unit = {},
        onFinish: () -> Unit = {},
    ) {
        getGitHubRepos.invoke(
            scope = scope,
            onStart = onStart,
            onFetchParam = {
                GetGitHubReposRepoParam.default(username = userName.value)
            },
            onOmitResult = {
                it.onSuccess { data ->
                    gitHubRepos.value = data.toUi()
                }.onFailure { error ->
                    handleError(error)
                }
            },
            onFinish = onFinish,
        )
    }

    fun getGitHubEvents(
        onStart: () -> Unit = {},
        onFinish: () -> Unit = {},
    ) {
        getGitHubEvents.invoke(
            scope = scope,
            onStart = onStart,
            onFetchParam = {
                GetGitHubEventsRepoParam.default(username = userName.value)
            },
            onOmitResult = {
                it.onSuccess { data ->
                    gitHubEvents.value = data.toUi()
                }.onFailure { error ->
                    handleError(error)
                }
            },
            onFinish = onFinish,
        )
    }

    private fun isAllReposLoaded(): Boolean {
        return gitHubUserProfile.value.publicRepos == gitHubRepos.value.size
    }

    private fun isAllEventsLoaded(): Boolean {
        return properties.lastFetchEventsIsEmpty && gitHubEvents.value.isNotEmpty()
    }

    private suspend fun handleError(error: Throwable) {
        val errorCode = when (error) {
            is Exzeption -> handleExzeptionError(error.status)
            is SocketTimeoutException -> ProfileMessageCode.TimeOutError
            is UnknownHostException -> ProfileMessageCode.NetworkError
            else -> ProfileMessageCode.UnexpectedError
        }

        event.emit(ProfileEvent.Notify(errorCode))
    }

    private suspend fun handleExzeptionError(status: Status): ProfileMessageCode {
        return when (status) {
            Status.NotFound -> ProfileMessageCode.DataNotFound
            Status.ReadError -> ProfileMessageCode.ReadDataError
            Status.Unauthorized -> {
                event.emit(ProfileEvent.LoggedOut)
                ProfileMessageCode.Unauthorized
            }
            is Status.Instance -> handleErrorStatusCode(status)
            else -> ProfileMessageCode.UnexpectedError
        }
    }

    private fun handleErrorStatusCode(status: Status.Instance): ProfileMessageCode {
        return when (status) {
            StatusExt.EmptyResult -> {
                properties.lastFetchEventsIsEmpty = true
                ProfileMessageCode.EmptyResult
            }
            else -> ProfileMessageCode.UnexpectedError
        }
    }
}