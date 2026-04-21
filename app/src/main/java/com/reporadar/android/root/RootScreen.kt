package com.reporadar.android.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.reporadar.android.R
import com.reporadar.searchautocomplete.domain.SearchResultItem

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RootScreen(
    selectedSearchResult: SearchResultItem? = null,
    onSearchButtonClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                actions = {
                    IconButton(
                        onClick = onSearchButtonClick
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.search_icon),
                            contentDescription = stringResource(id = R.string.search_button_content_description)
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) {
                val bodyText = when (val selected = selectedSearchResult) {
                    null -> stringResource(R.string.root_placeholder)
                    is SearchResultItem.User -> stringResource(
                        R.string.root_selected_user,
                        selected.loginName
                    )
                    is SearchResultItem.Repository -> stringResource(
                        R.string.root_selected_repository,
                        selected.repositoryName
                    )
                }
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(all = 16.dp),
                    text = bodyText,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    )
}

@Preview
@Composable
fun RootScreenPreview() {
    RootScreen(
        selectedSearchResult = null,
    ) {
        //nothing here for the preview
    }
}
