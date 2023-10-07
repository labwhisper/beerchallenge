package com.labwhisper.beerchallenge.navigation

data class NavigationDestination(val screen: Screen, val args: Map<String, Any> = emptyMap())
