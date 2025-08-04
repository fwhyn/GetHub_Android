package com.fwhyn.app.gethub.feature.func.user.data.repository

import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUserProfileRepoParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubUserProfileData
import com.fwhyn.lib.baze.common.helper.BaseRunner

abstract class GetGitHubUserProfileRepository : BaseRunner<GetGitHubUserProfileRepoParam, GitHubUserProfileData>()