package com.fwhyn.app.gethub.feature.screen.home

import androidx.lifecycle.viewModelScope
import com.fwhyn.app.gethub.common.helper.StatusExt
import com.fwhyn.app.gethub.common.helper.emitEvent
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase.LogoutUseCase
import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUsersRepoParam
import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubUsersRepository
import com.fwhyn.app.gethub.feature.screen.home.component.HomeMessageCode
import com.fwhyn.app.gethub.feature.screen.home.helper.extension.toUi
import com.fwhyn.app.gethub.feature.screen.home.model.GitHubUserUi
import com.fwhyn.app.gethub.feature.screen.home.model.HomeEvent
import com.fwhyn.app.gethub.feature.screen.home.model.HomeProperties
import com.fwhyn.app.gethub.feature.screen.home.model.HomeState
import com.fwhyn.lib.baze.common.model.Exzeption
import com.fwhyn.lib.baze.common.model.Status
import com.fwhyn.lib.baze.compose.model.CommonProperties
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGitHubUsers: GetGitHubUsersRepository,
    private val logoutUseCase: LogoutUseCase,
) : HomeVmInterface() {

    private val scope: CoroutineScope
        get() = viewModelScope

    private val event: MutableSharedFlow<HomeEvent> = MutableSharedFlow()
    private val gitHubUsers: MutableStateFlow<List<GitHubUserUi>> = MutableStateFlow(emptyList())
    private val querySuggestions: MutableStateFlow<List<GitHubUserUi>> = MutableStateFlow(emptyList())
    private val query: MutableStateFlow<String> = MutableStateFlow("")

    // ----------------------------------------------------------------
    override val commonProp: CommonProperties = CommonProperties()

    override val properties: HomeProperties = HomeProperties(
        event = event,
        gitHubUsers = gitHubUsers,
        querySuggestions = querySuggestions,
        query = query,
    )

    // ----------------------------------------------------------------
    init {
        getGitHubUsers()
    }

    // ----------------------------------------------------------------
    override fun onGoToProfile(user: String) {
        event.emitEvent(scope, HomeEvent.GoToProfile(user))
    }

    override fun onLogout() {
        logout()
    }

    override fun onLoadNext() {
        getGitHubUsers()
    }

    override fun onQueryChange(query: String) {
        this.query.value = query
        querySuggestions.value = gitHubUsers.filterByQuery(query)
    }

    override fun onSearch(query: String) {
        event.emitEvent(scope, HomeEvent.GoToProfile(query))
    }

    override fun onClearQuery() {
        query.value = ""
        querySuggestions.value = emptyList()
    }

    // ----------------------------------------------------------------
    private fun getGitHubUsers(param: GetGitHubUsersRepoParam = GetGitHubUsersRepoParam.default()) {
        val id = getGitHubUsers.getId()
        getGitHubUsers.invoke(
            scope = scope,
            onStart = { showLoading(id) },
            onFetchParam = { param },
            onOmitResult = {
                it.onSuccess { data ->
                    gitHubUsers.value = data.toUi()
                }.onFailure { error ->
                    handleError(error)
                }
            },
            onFinish = { dismissLoading(id) },
        )
    }

    private fun MutableStateFlow<List<GitHubUserUi>>.filterByQuery(query: String): List<GitHubUserUi> {
        val result = this.value.filter { it.login.contains(query, ignoreCase = true) }
        return result
    }

    private fun logout() {
        val id = logoutUseCase.getId()
        logoutUseCase.invoke(
            scope = scope,
            onStart = { showLoading(id) },
            onFetchParam = {},
            onOmitResult = {
                it.onSuccess {
                    event.emit(HomeEvent.LoggedOut)
                }.onFailure { error ->
                    handleError(error)
                }
            },
            onFinish = { dismissLoading(id) },
        )
    }

    private fun showLoading(tag: String) {
        commonProp.showDialog(tag, HomeState.Loading)
    }

    private fun dismissLoading(tag: String) {
        commonProp.dismissDialog(tag)
    }

    private suspend fun handleError(error: Throwable) {
        val errorCode = when (error) {
            is Exzeption -> handleExzeptionError(error.status)
            is SocketTimeoutException -> HomeMessageCode.TimeOutError
            is UnknownHostException -> HomeMessageCode.NetworkError
            else -> HomeMessageCode.UnexpectedError
        }

        event.emit(HomeEvent.Notify(errorCode))
    }

    private fun handleExzeptionError(status: Status): HomeMessageCode {
        return when (status) {
            Status.NotFound -> HomeMessageCode.DataNotFound
            Status.ReadError -> HomeMessageCode.ReadDataError
            Status.Unauthorized -> {
                logout()
                HomeMessageCode.Unauthorized
            }

            is Status.Instance -> handleErrorStatusCode(status)
            else -> HomeMessageCode.UnexpectedError
        }
    }

    private fun handleErrorStatusCode(status: Status.Instance): HomeMessageCode {
        return when (status) {
            StatusExt.EmptyResult -> HomeMessageCode.EmptyResult
            StatusExt.LogoutError -> HomeMessageCode.LogoutError
            else -> HomeMessageCode.UnexpectedError
        }
    }
}