package com.fwhyn.app.gethub.feature.screen.profile

import androidx.lifecycle.viewModelScope
import com.fwhyn.app.gethub.feature.func.event.data.model.GetGitHubEventsRepoParam
import com.fwhyn.app.gethub.feature.func.event.data.repository.GetGitHubEventsRepository
import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubReposRepoParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUserProfileRepoParam
import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubReposRepository
import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubUserProfileRepository
import com.fwhyn.app.gethub.feature.screen.home.component.HomeMessageCode
import com.fwhyn.app.gethub.feature.screen.profile.helper.extension.toUi
import com.fwhyn.app.gethub.feature.screen.profile.model.GitHubEventUi
import com.fwhyn.app.gethub.feature.screen.profile.model.GitHubRepoUi
import com.fwhyn.app.gethub.feature.screen.profile.model.GitHubUserProfileUi
import com.fwhyn.app.gethub.feature.screen.profile.model.ProfileEvent
import com.fwhyn.app.gethub.feature.screen.profile.model.ProfileProperties
import com.fwhyn.app.gethub.feature.screen.profile.model.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import java.net.SocketTimeoutException
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
    private val state = MutableStateFlow(ProfileState.Idle)
    private val gitHubUserProfile = MutableStateFlow(GitHubUserProfileUi.default())
    private val gitHubRepos = MutableStateFlow<List<GitHubRepoUi>>(emptyList())
    private val gitHubEvents = MutableStateFlow<List<GitHubEventUi>>(emptyList())

    // ----------------------------------------------------------------
    override val properties: ProfileProperties = ProfileProperties(
        event = event,
        state = state,
        userName = MutableStateFlow(null),
        gitHubUserProfile = gitHubUserProfile,
        gitHubRepos = gitHubRepos,
        gitHubEvents = gitHubEvents,
    )

    // ----------------------------------------------------------------
    init {
        loadGitHubUserProfile(properties.userName.value)
    }

    // ----------------------------------------------------------------
    override fun onLoadNextRepos() {
        getGitHubRepos(
            param = GetGitHubReposRepoParam.default(username = username),
            onStart = { state.value = ProfileState.Loading },
            onFinish = { state.value = ProfileState.Idle }
        )
    }

    override fun onLoadNextEvents() {
        getGitHubEvents(
            param = GetGitHubEventsRepoParam.default(username = username),
            onStart = { state.value = ProfileState.Loading },
            onFinish = { state.value = ProfileState.Idle }
        )
    }

    // ----------------------------------------------------------------
    // TODO add username passing parameters
    private fun loadGitHubUserProfile(username: String?) {
        // Prevent multiple calls
        if (state.value == ProfileState.Loading) return

        val gitHubEvents = {
            getGitHubEvents(
                param = GetGitHubEventsRepoParam.default(username = username),
                onFinish = { state.value = ProfileState.Idle }
            )
        }

        val gitHubRepos = {
            getGitHubRepos(
                param = GetGitHubReposRepoParam.default(username = username),
                onFinish = { gitHubEvents }
            )
        }

        getGitHubUserProfile(
            param = GetGitHubUserProfileRepoParam(username = username),
            onStart = { state.value = ProfileState.Loading },
            onFinish = { gitHubRepos },
        )
    }

    private fun getGitHubUserProfile(
        param: GetGitHubUserProfileRepoParam,
        onStart: () -> Unit = {},
        onFinish: () -> Unit = {},
    ) {
        // Prevent multiple calls
        if (state.value == ProfileState.Loading) return

        getGitHubUserProfile.invoke(
            scope = scope,
            onStart = onStart,
            onFetchParam = { param },
            onOmitResult = {
                it.onSuccess { data ->
                    gitHubUserProfile.value = data.toUi()
                }.onFailure { error ->
                    handleError(error)
                }
            },
            onFinish = onFinish,
        )
    }

    fun getGitHubRepos(
        param: GetGitHubReposRepoParam,
        onStart: () -> Unit = {},
        onFinish: () -> Unit = {},
    ) {
        // Prevent multiple calls
        if (state.value == ProfileState.Loading) return

        getGitHubRepos.invoke(
            scope = scope,
            onStart = onStart,
            onFetchParam = { param },
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
        param: GetGitHubEventsRepoParam,
        onStart: () -> Unit = {},
        onFinish: () -> Unit = {},
    ) {
        // Prevent multiple calls
        if (state.value == ProfileState.Loading) return

        getGitHubEvents.invoke(
            scope = scope,
            onStart = onStart,
            onFetchParam = { param },
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

    private suspend fun handleError(error: Throwable) {
        val errorCode = when (error) {
            is SocketTimeoutException -> HomeMessageCode.TimeOutError
            else -> HomeMessageCode.UnexpectedError
        }

        event.emit(ProfileEvent.Notify(errorCode))
    }
}