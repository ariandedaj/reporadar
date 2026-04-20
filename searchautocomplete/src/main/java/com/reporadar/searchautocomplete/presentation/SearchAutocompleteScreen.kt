package com.reporadar.searchautocomplete.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.reporadar.searchautocomplete.presentation.view.BackButton
import com.reporadar.searchautocomplete.presentation.view.SearchField
import com.reporadar.searchautocomplete.presentation.view.SearchResultsView
import com.reporadar.searchautocomplete.presentation.viewmodel.SearchAutocompleteViewModel
import com.reporadar.searchautocomplete.presentation.viewmodel.factory.createSearchAutocompleteViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchAutocompleteScreen(
    viewModel: SearchAutocompleteViewModel = createSearchAutocompleteViewModel(),
    onBackButtonClick: () -> Unit
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    SearchField(
                        searchQuery = searchQuery
                    ) { newText ->
                        viewModel.onSearchTextChange(
                            newText = newText
                        )
                    }
                },
                navigationIcon = {
                    BackButton { onBackButtonClick() }
                }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = innerPadding)
            ) {
                SearchResultsView()
            }
        }
    )
}

@Preview
@Composable
fun SearchAutocompleteScreenPreview() {
    SearchAutocompleteScreen {
        //nothing here for the preview
    }
}