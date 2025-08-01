package com.fwhyn.app.gethub.feature.screen.home

import androidx.lifecycle.viewModelScope
import com.fwhyn.app.gethub.common.helper.emitEvent
import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUsersParam
import com.fwhyn.app.gethub.feature.func.user.data.repository.GitHubUsersRepository
import com.fwhyn.app.gethub.feature.screen.home.component.HomeMessageCode
import com.fwhyn.app.gethub.feature.screen.home.helper.extension.toUi
import com.fwhyn.app.gethub.feature.screen.home.model.GitHubUserUi
import com.fwhyn.app.gethub.feature.screen.home.model.HomeEvent
import com.fwhyn.app.gethub.feature.screen.home.model.HomeProperties
import com.fwhyn.app.gethub.feature.screen.home.model.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGitHubUsers: GitHubUsersRepository,
) : HomeVmInterface() {

    private val scope: CoroutineScope
        get() = viewModelScope

    private val event: MutableSharedFlow<HomeEvent> = MutableSharedFlow()
    private val state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Idle)
    private val gitHubUsers: MutableStateFlow<List<GitHubUserUi>> = MutableStateFlow(emptyList())

    // ----------------------------------------------------------------
    override val properties: HomeProperties = HomeProperties(
        event = event,
        state = state,
        gitHubUsers = gitHubUsers
    )

    // ----------------------------------------------------------------
    init {
        getGitHubUsers(GetGitHubUsersParam.default())
    }

    // ----------------------------------------------------------------
    override fun onOpenProfile(user: String) {
        event.emitEvent(scope, HomeEvent.OpenProfile(user))
    }

    // ----------------------------------------------------------------
    private fun getGitHubUsers(param: GetGitHubUsersParam) {
        getGitHubUsers.invoke(
            scope = scope,
            onStart = { state.value = HomeState.Loading },
            onFetchParam = { param },
            onOmitResult = {
                it.onSuccess { data ->
                    gitHubUsers.value = data.toUi()
                }.onFailure { error ->
                    handleError(error)
                }
            },
            onFinish = { state.value = HomeState.Idle },
        )
    }

    private suspend fun handleError(error: Throwable) {
        val errorCode = when (error) {
            is SocketTimeoutException -> HomeMessageCode.TimeOutError
            else -> HomeMessageCode.UnexpectedError
        }

        event.emit(HomeEvent.Notify(errorCode))
    }
}