package com.reporadar.searchautocomplete.presentation.view.result

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.reporadar.searchautocomplete.domain.SearchResultItem
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun SearchResultsView(
    searchResultItems: List<SearchResultItem>,
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
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        itemsIndexed(
            items = searchResultItems,
            key = { _, item -> item.itemKey() }
        ) { index, item ->
            SearchResultItemView(
                modifier = Modifier.padding(
                    top = if (index == 0) 16.dp else 0.dp,
                    bottom = if (index == searchResultItems.lastIndex) 16.dp else 0.dp
                ),
                item = item
            )
        }
    }
}

@Composable
fun SearchResultItemView(
    modifier: Modifier = Modifier,
    item: SearchResultItem
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = item.itemTitle(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

private fun SearchResultItem.itemKey(): String {
    return when (this) {
        is SearchResultItem.Repository -> "repo-${this.id}"
        is SearchResultItem.User -> "user-${this.id}"
    }
}

private fun SearchResultItem.itemTitle(): String {
    return when (this) {
        is SearchResultItem.Repository -> this.repositoryName
        is SearchResultItem.User -> this.loginName
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
        )
    )
}