package com.reporadar.searchautocomplete.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.reporadar.searchautocomplete.presentation.view.BackButton
import com.reporadar.searchautocomplete.presentation.view.SearchField
import com.reporadar.searchautocomplete.presentation.view.result.SearchResultsView
import com.reporadar.searchautocomplete.presentation.view.error.ErrorView
import com.reporadar.searchautocomplete.presentation.view.idle.IdleView
import com.reporadar.searchautocomplete.presentation.view.loading.LoadingView
import com.reporadar.searchautocomplete.presentation.view.noresults.NoResultsView
import com.reporadar.searchautocomplete.presentation.viewmodel.SearchAutocompleteUiState
import com.reporadar.searchautocomplete.presentation.viewmodel.SearchAutocompleteViewModel
import com.reporadar.searchautocomplete.presentation.viewmodel.factory.createSearchAutocompleteViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchAutocompleteScreen(
    viewModel: SearchAutocompleteViewModel = createSearchAutocompleteViewModel(),
    onBackButtonClick: () -> Unit
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.surfaceContainerHigh
                    } else {
                        Color.LightGray
                    }
                ),
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
                when (val state = uiState) {
                    SearchAutocompleteUiState.Idle -> IdleView()

                    is SearchAutocompleteUiState.Loading -> LoadingView()

                    is SearchAutocompleteUiState.Failure -> ErrorView(
                        errorType = state.errorType,
                        onRetryButtonClick = { viewModel.onRetryButtonClick() }
                    )

                    is SearchAutocompleteUiState.Success -> SearchResultsView(
                        searchResultItems = state.items
                    )

                    SearchAutocompleteUiState.NoResults -> NoResultsView(
                        searchQuery = searchQuery
                    )
                }
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