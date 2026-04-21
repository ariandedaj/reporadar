package com.reporadar.searchautocomplete.presentation.viewmodel

import kotlinx.coroutines.flow.StateFlow

interface SearchAutocompleteViewModel {

    val searchQuery: StateFlow<String>

    val uiState: StateFlow<SearchAutocompleteUiState>

    fun onSearchTextChange(newText: String)

    fun onRetryButtonClick()

}