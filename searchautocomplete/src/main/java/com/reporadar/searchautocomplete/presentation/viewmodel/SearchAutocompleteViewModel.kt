package com.reporadar.searchautocomplete.presentation.viewmodel

import kotlinx.coroutines.flow.StateFlow

interface SearchAutocompleteViewModel {

    val searchQuery: StateFlow<String>

    fun onSearchTextChange(newText: String)

}