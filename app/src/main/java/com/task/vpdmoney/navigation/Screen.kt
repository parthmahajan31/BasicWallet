package com.task.vpdmoney.navigation

sealed class Screen(val route: String, val name: String) {
    data object Login : Screen(route = "login", name = "Login")
    data object Home : Screen(route = "home", name = "Home")
}