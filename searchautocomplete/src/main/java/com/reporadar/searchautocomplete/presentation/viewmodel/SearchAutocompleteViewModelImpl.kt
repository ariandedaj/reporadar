package com.reporadar.searchautocomplete.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reporadar.searchautocomplete.data.GithubSearchRepository
import com.reporadar.searchautocomplete.data.result.SearchResult
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class SearchAutocompleteViewModelImpl(
    private val repository: GithubSearchRepository
) : ViewModel(), SearchAutocompleteViewModel {

    private val _searchQuery = MutableStateFlow(value = "")
    override val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow<SearchAutocompleteUiState>(
        value = SearchAutocompleteUiState.Idle
    )
    override val uiState: StateFlow<SearchAutocompleteUiState> = _uiState.asStateFlow()

    init {
        bindSearchQueryToUiState()
    }

    @OptIn(FlowPreview::class)
    private fun bindSearchQueryToUiState() {
        viewModelScope.launch {
            _searchQuery.debounce(timeoutMillis = SEARCH_DEBOUNCE_MS)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isBlank() || query.length < MIN_QUERY_LENGTH) {
                        _uiState.value = SearchAutocompleteUiState.Idle
                        return@collectLatest
                    }
                    performSearch(query = query)
                }
        }
    }

    private suspend fun performSearch(query: String) {
        _uiState.value = SearchAutocompleteUiState.Loading
        _uiState.value = when (val result = repository.search(query)) {
            is SearchResult.Success -> if (result.items.isEmpty()) {
                SearchAutocompleteUiState.NoResults
            } else {
                SearchAutocompleteUiState.Success(items = result.items)
            }

            is SearchResult.Error -> SearchAutocompleteUiState.Failure(
                errorType = result.errorType
            ).also { e ->
                Log.e("SearchAutocompleteViewModelImpl", e.errorType.toString())
            }
        }
    }

    override fun onSearchTextChange(newText: String) {
        _searchQuery.value = newText
    }

    override fun onRetryButtonClick() {
        val query = _searchQuery.value
        if (query.isBlank() || query.length < MIN_QUERY_LENGTH) {
            return
        }
        viewModelScope.launch {
            performSearch(query = query)
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_MS = 300L
        private const val MIN_QUERY_LENGTH = 3
    }

}