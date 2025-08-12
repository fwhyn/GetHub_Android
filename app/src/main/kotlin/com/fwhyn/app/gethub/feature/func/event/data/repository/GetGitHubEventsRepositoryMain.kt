package com.fwhyn.app.gethub.feature.func.event.data.repository

import com.fwhyn.app.gethub.common.helper.Constant.TIMEOUT_MILLIS
import com.fwhyn.app.gethub.common.helper.StatusExt
import com.fwhyn.app.gethub.feature.func.event.data.model.GetGitHubEventsRepoParam
import com.fwhyn.app.gethub.feature.func.event.data.model.GitHubEventData
import com.fwhyn.app.gethub.feature.func.event.data.remote.GitHubEventsRemoteDataSource
import com.fwhyn.lib.baze.common.model.Exzeption
import com.fwhyn.lib.baze.common.model.Status
import retrofit2.Response
import javax.inject.Inject

class GetGitHubEventsRepositoryMain @Inject constructor(
    private val gitHubEventsRemoteDataSource: GitHubEventsRemoteDataSource,
) : GetGitHubEventsRepository() {

    private val loadedData: MutableSet<GitHubEventData> = mutableSetOf()
    private var pageToLoad: Int = 1

    init {
        setTimeOutMillis(TIMEOUT_MILLIS)
    }

    override suspend fun onRunning(
        param: GetGitHubEventsRepoParam,
        result: suspend (List<GitHubEventData>) -> Unit,
    ) {
        val response: Response<List<GitHubEventData>> = gitHubEventsRemoteDataSource.getEvents(
            username = param.username,
            perPage = param.perPage,
            page = pageToLoad
        )

        if (response.isSuccessful) {
            val data = response.body()
            if (data == null) throw Exzeption(Status.NotFound)
            if (data.isEmpty()) throw Exzeption(StatusExt.EmptyResult)

            loadedData.addAll(data)
            pageToLoad++

            result(loadedData.toList())
        } else {
            when (response.code()) {
                401 -> throw Exzeption(Status.Unauthorized)
                else -> throw Exzeption(
                    status = Status.ReadError,
                    throwable = Throwable("Error fetching events: ${response.errorBody()?.string()}")
                )
            }
        }
    }

}