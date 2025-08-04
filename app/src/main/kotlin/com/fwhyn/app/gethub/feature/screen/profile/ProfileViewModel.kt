package com.fwhyn.app.gethub.feature.screen.profile

import androidx.lifecycle.viewModelScope
import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUserProfileRepoParam
import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubUserProfileRepository
import com.fwhyn.app.gethub.feature.screen.home.component.HomeMessageCode
import com.fwhyn.app.gethub.feature.screen.home.model.HomeState
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
) : ProfileVmInterface() {

    private val scope: CoroutineScope
        get() = viewModelScope

    private val event: MutableSharedFlow<ProfileEvent> = MutableSharedFlow()
    private val state: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState.Idle)
    private val gitHubUserProfile: MutableStateFlow<GitHubUserProfileUi> =
        MutableStateFlow(GitHubUserProfileUi.default())
    private val gitHubRepos: MutableStateFlow<List<GitHubRepoUi>> = MutableStateFlow(emptyList())
    private val gitHubEvents: MutableStateFlow<List<GitHubEventUi>> = MutableStateFlow(emptyList())

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
        getGitHubUserProfile()
    }

    // ----------------------------------------------------------------
    // override

    // ----------------------------------------------------------------
    // TODO add username passing parameters
    private fun getGitHubUserProfile(param: GetGitHubUserProfileRepoParam = GetGitHubUserProfileRepoParam.default()) {
        // Prevent multiple calls
        if (state.value == HomeState.Loading) return

        // Fetch GitHub users
        getGitHubUserProfile.invoke(
            scope = scope,
            onStart = { state.value = ProfileState.Loading },
            onFetchParam = { param },
            onOmitResult = {
                it.onSuccess { data ->
                    gitHubUserProfile.value = data.toUi()
                }.onFailure { error ->
                    handleError(error)
                }
            },
            onFinish = { state.value = ProfileState.Idle },
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