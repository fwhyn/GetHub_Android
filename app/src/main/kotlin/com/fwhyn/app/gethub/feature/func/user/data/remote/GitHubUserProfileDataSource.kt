package com.fwhyn.app.gethub.feature.func.user.data.remote

import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUserProfileParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubUserProfileData
import com.fwhyn.lib.baze.common.helper.BaseGetter

interface GitHubUserProfileDataSource : BaseGetter<GetGitHubUserProfileParam, GitHubUserProfileData>