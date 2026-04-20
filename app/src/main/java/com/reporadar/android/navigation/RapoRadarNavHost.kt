package com.reporadar.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reporadar.android.view.RootScreen
import com.reporadar.searchautocomplete.presentation.SearchAutocompleteScreen

private const val RootScreen = "root_screen"
private const val SearchAutoCompleteScreen = "search_autocomplete_screen"

@Composable
fun RepoRadarNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = RootScreen,
        enterTransition = { slideInFromRight() },
        exitTransition = { slideOutToLeft() },
        popEnterTransition = { slideInFromLeft() },
        popExitTransition = { slideOutToRight() }
    ) {
        composable(
            route = RootScreen
        ) {
            RootScreen {
                navController.navigate(
                    route = SearchAutoCompleteScreen
                )
            }
        }

        composable(
            route = SearchAutoCompleteScreen
        ) {
            SearchAutocompleteScreen {
                navController.popBackStack()
            }
        }
    }
}
