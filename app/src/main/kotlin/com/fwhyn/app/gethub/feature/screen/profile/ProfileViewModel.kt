package com.fwhyn.app.gethub.feature.screen.profile

import androidx.lifecycle.viewModelScope
import com.fwhyn.app.gethub.common.helper.extension.StatusExt
import com.fwhyn.app.gethub.common.helper.extension.emitEvent
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase.LogoutUseCase
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
import com.fwhyn.lib.baze.compose.model.CommonProperties
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.TimeoutCancellationException
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
    private val logoutUseCase: LogoutUseCase,
) : ProfileVmInterface() {

    private val scope: CoroutineScope
        get() = viewModelScope

    private val event = MutableSharedFlow<ProfileEvent>()
    private val userName = MutableStateFlow("")
    private val gitHubUserProfile = MutableStateFlow(GitHubUserProfileUi.default())
    private val gitHubRepos = MutableStateFlow<List<GitHubRepoUi>>(emptyList())
    private val gitHubEvents = MutableStateFlow<List<GitHubEventUi>>(emptyList())

    // ----------------------------------------------------------------
    override val commonProp: CommonProperties = CommonProperties()

    override val properties: ProfileProperties = ProfileProperties(
        event = event,
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

    override fun onRefresh() {
        loadGitHubUserProfile()
    }

    override fun onLoadNextRepos() {
        getGitHubRepos()
    }

    override fun onLoadNextEvents() {
        getGitHubEvents()
    }

    // ----------------------------------------------------------------
    private fun loadGitHubUserProfile() {
        val id = getGitHubUserProfile.getId()
        getGitHubUserProfile.invoke(
            scope = scope,
            onStart = { showLoading(id) },
            onFetchParam = {
                GetGitHubUserProfileRepoParam(username = userName.value)
            },
            onOmitResult = {
                it.onSuccess { data ->
                    gitHubUserProfile.value = data.toUi()
                    getGitHubRepos()
                    getGitHubEvents()
                }.onFailure { error ->
                    handleError(error)
                }
            },
            onFinish = { dismissLoading(id) }
        )
    }

    fun getGitHubRepos() {
        if (isUnableToLoadMoreRepos()) return

        val id = getGitHubRepos.getId()
        getGitHubRepos.invoke(
            scope = scope,
            onStart = { showLoading(id) },
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
            onFinish = { dismissLoading(id) },
        )
    }

    fun getGitHubEvents() {
        if (isAllEventsLoaded()) return

        val id = getGitHubEvents.getId()
        getGitHubEvents.invoke(
            scope = scope,
            onStart = { showLoading(id) },
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
            onFinish = { dismissLoading(id) },
        )
    }

    private fun isUnableToLoadMoreRepos(): Boolean {
        return isAllReposLoaded() && gitHubRepos.value.isNotEmpty()
    }

    private fun isAllReposLoaded(): Boolean {
        return gitHubUserProfile.value.publicRepos == gitHubRepos.value.size
    }

    private fun isAllEventsLoaded(): Boolean {
        return properties.lastFetchEventsIsEmpty && gitHubEvents.value.isNotEmpty()
    }

    private fun logout() {
        val id = logoutUseCase.getId()
        logoutUseCase.invoke(
            scope = scope,
            onStart = { showLoading(id) },
            onFetchParam = {},
            onOmitResult = {
                it.onSuccess {
                    event.emit(ProfileEvent.LoggedOut)
                }.onFailure { error ->
                    handleError(error)
                }
            },
            onFinish = { dismissLoading(id) },
        )
    }

    private fun showLoading(tag: String) {
        commonProp.showDialog(tag, ProfileState.Loading)
    }

    private fun dismissLoading(tag: String) {
        commonProp.dismissDialog(tag)
    }

    private suspend fun handleError(error: Throwable) {
        val errorCode = when (error) {
            is Exzeption -> handleExzeptionError(error.status)
            is TimeoutCancellationException,
            is SocketTimeoutException,
                -> ProfileMessageCode.TimeOutError
            is UnknownHostException -> ProfileMessageCode.NetworkError
            else -> ProfileMessageCode.UnexpectedError
        }

        event.emit(ProfileEvent.Notify(errorCode))
    }

    private fun handleExzeptionError(status: Status): ProfileMessageCode {
        return when (status) {
            Status.NotFound -> ProfileMessageCode.DataNotFound
            Status.ReadError -> ProfileMessageCode.ReadDataError
            Status.Unauthorized -> {
                logout()
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