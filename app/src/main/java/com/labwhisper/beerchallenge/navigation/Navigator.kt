package com.labwhisper.beerchallenge.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {

    private val _destination: MutableStateFlow<NavigationDestination> =
        MutableStateFlow(NavigationDestination(Screen.List))
    val destination: MutableStateFlow<NavigationDestination> = _destination

    suspend fun navigateTo(navigationDestination: NavigationDestination) {
        _destination.emit(navigationDestination)
    }

}
