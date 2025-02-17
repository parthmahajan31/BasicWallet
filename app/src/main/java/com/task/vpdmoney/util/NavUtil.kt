package com.task.vpdmoney.util

import androidx.navigation.NavController

fun NavController.navigateAndCleanBackStack(route: String) {
    navigate(route = route) {
        // Pop up to the start destination and include it in the pop-up operation
        popUpTo(graph.startDestinationId) { inclusive = true }
    }
    // Set the new start destination
    graph.setStartDestination(route)
}