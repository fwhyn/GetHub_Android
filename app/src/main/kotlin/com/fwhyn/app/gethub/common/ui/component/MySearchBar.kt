package com.fwhyn.app.gethub.common.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.inputFieldColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.ui.config.Grey90
import com.fwhyn.app.gethub.common.ui.config.Grey95
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.common.ui.config.PurpleGrey80
import com.fwhyn.app.gethub.feature.screen.home.component.GitHubUserView
import com.fwhyn.app.gethub.feature.screen.home.component.GitHubUserViewParam
import com.fwhyn.app.gethub.feature.screen.home.model.GitHubUserUi
import com.fwhyn.app.gethub.feature.screen.home.model.gitHubUsersUiFake
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    modifier: Modifier = Modifier,
    param: MySearchBarParam,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var query by rememberSaveable { mutableStateOf("") }
    query = param.query

    SearchBar(
        modifier = modifier,
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = param.onQueryChange,
                onSearch = {
                    param.onSearch(query)
                    expanded = false
                },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                placeholder = { Text(stringResource(R.string.search_by_account)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search_button),
                        tint = Grey90
                    )
                },
                trailingIcon = {
                    if (param.query.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.clear_search),
                            modifier = Modifier.clickable {
                                param.onClearQuery()
                                expanded = false
                            },
                            tint = Grey90
                        )
                    }
                },
                colors = inputFieldColors(
                    focusedTextColor = Grey95,
                    unfocusedTextColor = Grey90,
                )
            )
        },
        expanded = expanded,
        onExpandedChange = { expanded = it },
        colors = SearchBarDefaults.colors(
            containerColor = PurpleGrey80
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = stringResource(R.string.suggestions))

            MySpacer(8.dp)
            LazyColumn(
                modifier = Modifier
            ) {
                itemsIndexed(param.querySuggestions) { index, user ->
                    val gitHubUserViewParam = GitHubUserViewParam(
                        user = user,
                        onClicked = { param.onSearch(user.login) }
                    )

                    GitHubUserView(
                        modifier = Modifier.fillMaxWidth(),
                        param = gitHubUserViewParam,
                    )

                    if (index < param.querySuggestions.size - 1) {
                        // Add a spacer between items
                        HorizontalDivider()
                        MySpacer(1.dp)
                    }
                }
            }
        }
    }
}

data class MySearchBarParam(
    val querySuggestions: List<GitHubUserUi>,
    val query: String,
    val onQueryChange: (String) -> Unit,
    val onSearch: (String) -> Unit,
    val onClearQuery: () -> Unit,
) {
    companion object {
        fun default(
            querySuggestions: List<GitHubUserUi> = emptyList(),
            query: String,
            onQueryChange: (String) -> Unit,
            onSearch: (String) -> Unit,
            onClearQuery: () -> Unit,
        ) = MySearchBarParam(
            querySuggestions = querySuggestions,
            query = query,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
            onClearQuery = onClearQuery,
        )
    }
}

@Composable
fun getStateOfMySearchBarParam(
    querySuggestions: StateFlow<List<GitHubUserUi>>,
    queryFlow: StateFlow<String>,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onClearQuery: () -> Unit,
): MySearchBarParam {
    val suggestions by querySuggestions.collectAsStateWithLifecycle()
    val query by queryFlow.collectAsStateWithLifecycle()

    return MySearchBarParam(
        querySuggestions = suggestions,
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        onClearQuery = onClearQuery,
    )
}

@Composable
@Preview
fun SearchBarPreview() {
    MyTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val gitHubUsersFlow: MutableStateFlow<List<GitHubUserUi>> = MutableStateFlow(gitHubUsersUiFake)
            val queryFlow: MutableStateFlow<String> = MutableStateFlow("")

            MySearchBar(
                modifier = Modifier.fillMaxWidth(),
                param = getStateOfMySearchBarParam(
                    querySuggestions = gitHubUsersFlow,
                    queryFlow = queryFlow,
                    onQueryChange = { queryFlow.value = it },
                    onSearch = { /* Handle search action */ },
                    onClearQuery = { queryFlow.value = "" }
                )
            )
        }
    }
}