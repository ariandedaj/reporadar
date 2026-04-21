package com.reporadar.android.searchautocomplete

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.reporadar.searchautocomplete.presentation.SearchAutocompleteScreen

const val SEARCH_AUTOCOMPLETE_ROUTE = "search_autocomplete_screen"

@Composable
fun SearchAutocompleteRoute(
    navController: NavController
) {
    SearchAutocompleteScreen(
        onBackButtonClick = {
            navController.popBackStack()
        },
        onSearchResultItemClick = { item ->
            navController.popBackStackWithSearchSelection(item)
        }
    )
}
