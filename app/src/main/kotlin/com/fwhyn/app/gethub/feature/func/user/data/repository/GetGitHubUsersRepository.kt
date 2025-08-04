package com.fwhyn.app.gethub.feature.func.user.data.repository

import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUsersRepoParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubUserData
import com.fwhyn.lib.baze.common.helper.BaseRunner

abstract class GetGitHubUsersRepository : BaseRunner<GetGitHubUsersRepoParam, List<GitHubUserData>>()