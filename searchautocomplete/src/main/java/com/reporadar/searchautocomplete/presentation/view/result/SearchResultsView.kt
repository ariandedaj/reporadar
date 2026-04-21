package com.reporadar.searchautocomplete.presentation.view.result

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.reporadar.searchautocomplete.R
import com.reporadar.searchautocomplete.domain.SearchResultItem
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun SearchResultsView(
    searchResultItems: List<SearchResultItem>,
    onSearchResultItemClick: (SearchResultItem) -> Unit,
) {
    val listState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = searchResultItems,
            key = { item -> item.itemKey() }
        ) { item ->
            SearchResultItemView(
                item = item,
                onClick = { onSearchResultItemClick(item) },
            )
        }
    }
}

@Composable
fun SearchResultItemView(
    item: SearchResultItem,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = item.displayTitle(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

private fun SearchResultItem.itemKey(): String {
    return when (this) {
        is SearchResultItem.Repository -> "repo-${this.id}"
        is SearchResultItem.User -> "user-${this.id}"
    }
}

@Composable
private fun SearchResultItem.displayTitle(): String {
    return when (this) {
        is SearchResultItem.Repository -> stringResource(
            id = R.string.search_result_item_repository,
            this.repositoryName
        )

        is SearchResultItem.User -> stringResource(
            id = R.string.search_result_item_user,
            this.loginName
        )
    }
}

@Preview
@Composable
fun SearchResultsPreview(
) {
    SearchResultsView(
        searchResultItems = listOf(
            SearchResultItem.User(id = 1, loginName = "user"),
            SearchResultItem.Repository(id = 2, repositoryName = "reporadar")
        ),
        onSearchResultItemClick = {},
    )
}