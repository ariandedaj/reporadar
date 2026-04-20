package com.reporadar.searchautocomplete.presentation.viewmodel.factory

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reporadar.searchautocomplete.presentation.viewmodel.SearchAutocompleteViewModel
import com.reporadar.searchautocomplete.presentation.viewmodel.SearchAutocompleteViewModelImpl

@Composable
fun createSearchAutocompleteViewModel(): SearchAutocompleteViewModel {
    val factory = SearchAutocompleteViewModelFactory()
    val viewModel: SearchAutocompleteViewModelImpl = viewModel(factory = factory)
    return viewModel
}

private class SearchAutocompleteViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SearchAutocompleteViewModelImpl() as T
    }

}