package com.labwhisper.beerchallenge.navigation

import androidx.navigation.NavController

fun NavController.navigateToScreen(
    currentScreen: Screen, destination: NavigationDestination
) {
    if (destination.screen == currentScreen) {
        return
    }
    if (destination.screen == currentScreen.popupTo) {
        popBackStack()
        return
    }

    var finalRoute = destination.screen.route
    destination.args.forEach { (key, value) ->
        finalRoute = finalRoute.replace("{$key}", value.toString())
    }

    navigate(finalRoute)
}