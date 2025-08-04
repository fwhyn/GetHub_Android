package com.fwhyn.app.gethub.feature.func.event.data.repository

import com.fwhyn.app.gethub.feature.func.event.data.model.GetGitHubEventsRepoParam
import com.fwhyn.app.gethub.feature.func.event.data.model.GitHubEventData
import com.fwhyn.lib.baze.common.helper.BaseRunner

abstract class GetGitHubEventsRepository : BaseRunner<GetGitHubEventsRepoParam, List<GitHubEventData>>()