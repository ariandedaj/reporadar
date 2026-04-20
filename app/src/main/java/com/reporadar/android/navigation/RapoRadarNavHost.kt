package com.reporadar.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reporadar.android.view.RootScreen

private const val RootScreen = "root_screen"

@Composable
fun RepoRadarNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = RootScreen
    ) {
        composable(route = RootScreen) {
            RootScreen { }
        }
    }
}
