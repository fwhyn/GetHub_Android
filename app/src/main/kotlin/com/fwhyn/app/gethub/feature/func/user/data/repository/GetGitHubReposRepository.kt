package com.fwhyn.app.gethub.feature.func.user.data.repository

import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubReposRepoParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubRepoData
import com.fwhyn.lib.baze.common.helper.BaseRunner

abstract class GetGitHubReposRepository : BaseRunner<GetGitHubReposRepoParam, List<GitHubRepoData>>()