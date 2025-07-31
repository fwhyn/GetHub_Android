package com.fwhyn.app.gethub.feature.func.user.data.repository

import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUserProfileParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubUserProfileData
import com.fwhyn.lib.baze.common.helper.BaseRunner

abstract class GitHubUserProfileRepository : BaseRunner<GetGitHubUserProfileParam, GitHubUserProfileData>()