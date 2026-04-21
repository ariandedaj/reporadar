package com.reporadar.searchautocomplete.presentation.viewmodel

import com.reporadar.searchautocomplete.data.result.FetchDataErrorType
import com.reporadar.searchautocomplete.domain.SearchResultItem

sealed class SearchAutocompleteUiState {

    data object Idle : SearchAutocompleteUiState()

    data object Loading : SearchAutocompleteUiState()

    data class Success(
        val items: List<SearchResultItem>
    ) : SearchAutocompleteUiState()

    object NoResults : SearchAutocompleteUiState()

    data class Failure(
        val errorType: FetchDataErrorType
    ) : SearchAutocompleteUiState()

}
