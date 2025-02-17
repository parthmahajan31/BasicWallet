package com.task.vpdmoney.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.task.vpdmoney.ui.screen.home.HomeScreen
import com.task.vpdmoney.ui.screen.login.LoginScreen
import com.task.vpdmoney.util.fadeInTransition
import com.task.vpdmoney.util.fadeOutTransition
import com.task.vpdmoney.util.navigateAndCleanBackStack

const val NAVIGATION_HOST_ROUTE = "navigation_host_route"

@SuppressLint("RestrictedApi", "ContextCastToActivity")
@Composable
fun AppNavigationGraph(
    auth: FirebaseAuth
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (auth.currentUser != null) {
            Screen.Home.route
        } else {
            Screen.Login.route
        },
        route = NAVIGATION_HOST_ROUTE,
        enterTransition = { fadeInTransition },
        exitTransition = { fadeOutTransition },
        popEnterTransition = { fadeInTransition },
        popExitTransition = { fadeOutTransition },
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToHome = { navController.navigateToHome() }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateLogin = { navController.navigateToLogin() }
            )
        }
    }
}

fun NavController.navigateToHome() = navigateAndCleanBackStack(Screen.Home.route)

fun NavController.navigateToLogin() = navigate(Screen.Login.route)