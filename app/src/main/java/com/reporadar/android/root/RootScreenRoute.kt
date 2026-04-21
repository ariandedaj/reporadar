package com.reporadar.android.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.reporadar.android.searchautocomplete.SEARCH_AUTOCOMPLETE_ROUTE
import com.reporadar.android.searchautocomplete.rememberSelectedSearchResult

const val ROOT_SCREEN_ROUTE = "root_screen"

@Composable
fun RootScreenRoute(
    backStackEntry: NavBackStackEntry,
    navController: NavController,
) {
    val selectedSearchResult = rememberSelectedSearchResult(backStackEntry)
    RootScreen(
        selectedSearchResult = selectedSearchResult,
        onSearchButtonClick = {
            navController.navigate(route = SEARCH_AUTOCOMPLETE_ROUTE)
        },
    )
}
