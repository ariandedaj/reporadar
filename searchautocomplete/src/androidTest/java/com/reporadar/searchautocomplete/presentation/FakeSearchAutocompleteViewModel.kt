package com.reporadar.searchautocomplete.presentation

import com.reporadar.searchautocomplete.presentation.viewmodel.SearchAutocompleteUiState
import com.reporadar.searchautocomplete.presentation.viewmodel.SearchAutocompleteViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeSearchAutocompleteViewModel(
    searchQuery: String,
    uiState: SearchAutocompleteUiState,
) : SearchAutocompleteViewModel {

    private val _searchQuery = MutableStateFlow(searchQuery)
    override val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow(uiState)
    override val uiState: StateFlow<SearchAutocompleteUiState> = _uiState.asStateFlow()

    override fun onSearchTextChange(newText: String) {
        _searchQuery.value = newText
    }

    override fun onRetryButtonClick() {
        // no-op: screen tests only assert error UI is visible
    }
}
