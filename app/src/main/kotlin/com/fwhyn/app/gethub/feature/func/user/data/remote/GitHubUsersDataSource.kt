package com.fwhyn.app.gethub.feature.func.user.data.remote

import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUsersParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubUserData
import com.fwhyn.lib.baze.common.helper.BaseGetter

interface GitHubUsersDataSource : BaseGetter<GetGitHubUsersParam, GitHubUserData>