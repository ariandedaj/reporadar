package com.reporadar.searchautocomplete.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchAutocompleteViewModelImpl : ViewModel(), SearchAutocompleteViewModel {

    private val _searchQuery = MutableStateFlow(value = "")
    override val searchQuery: StateFlow<String> = _searchQuery

    override fun onSearchTextChange(newText: String) {
        _searchQuery.value = newText
        //TODO: implement search functionality
    }

}