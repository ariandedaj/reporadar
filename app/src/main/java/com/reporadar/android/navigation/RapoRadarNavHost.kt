package com.reporadar.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reporadar.android.root.ROOT_SCREEN_ROUTE
import com.reporadar.android.root.RootScreenRoute
import com.reporadar.android.searchautocomplete.SEARCH_AUTOCOMPLETE_ROUTE
import com.reporadar.android.searchautocomplete.SearchAutocompleteRoute

@Composable
fun RepoRadarNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ROOT_SCREEN_ROUTE,
        enterTransition = { slideInFromRight() },
        exitTransition = { slideOutToLeft() },
        popEnterTransition = { slideInFromLeft() },
        popExitTransition = { slideOutToRight() }
    ) {
        composable(route = ROOT_SCREEN_ROUTE) { backStackEntry ->
            RootScreenRoute(
                backStackEntry = backStackEntry,
                navController = navController,
            )
        }

        composable(route = SEARCH_AUTOCOMPLETE_ROUTE) {
            SearchAutocompleteRoute(navController = navController)
        }
    }
}
