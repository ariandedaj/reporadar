package com.reporadar.android.searchautocomplete

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.reporadar.searchautocomplete.domain.SearchResultItem

private object SearchSelectionKeys {
    const val SELECTED_RESULT = "selected_search_result"
}

internal fun SavedStateHandle.putSearchSelection(item: SearchResultItem) {
    this[SearchSelectionKeys.SELECTED_RESULT] = item
}

internal fun NavController.popBackStackWithSearchSelection(item: SearchResultItem) {
    previousBackStackEntry?.savedStateHandle?.putSearchSelection(item)
    popBackStack()
}

@Composable
internal fun rememberSelectedSearchResult(backStackEntry: NavBackStackEntry): SearchResultItem? {
    val selected by backStackEntry.savedStateHandle
        .getStateFlow<SearchResultItem?>(SearchSelectionKeys.SELECTED_RESULT, null)
        .collectAsState()
    return selected
}
