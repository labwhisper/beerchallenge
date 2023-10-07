package com.labwhisper.beerchallenge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.labwhisper.beerchallenge.beerdetails.BeerDetails
import com.labwhisper.beerchallenge.beerdetails.BeerDetailsViewModel
import com.labwhisper.beerchallenge.beerlist.BeerList
import com.labwhisper.beerchallenge.beerlist.BeerListViewModel

@Composable
fun ScreenSwitcher(navigator: Navigator) {

    val navController = rememberNavController()
    val destination by navigator.destination.collectAsState()


    LaunchedEffect(destination) {
        val currentScreen = navController.currentDestination?.route?.let { Screen.getByRoute(it) }
            ?: return@LaunchedEffect
        navController.navigateToScreen(currentScreen, destination)
    }
    val beerListViewModel: BeerListViewModel = hiltViewModel()
    val beerDetailsViewModel: BeerDetailsViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Screen.List.route) {

        composable(route = Screen.List.route) {
            BeerList(
                beerItemsStateFlow = beerListViewModel.beerUiModelPageStateFlow,
                onItemClick = { beerId -> beerListViewModel.onBeerSelected(beerId) },
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("beerId") { type = NavType.IntType }
            )
        ) {
            val beerId = it.arguments?.getInt("beerId")
            beerDetailsViewModel.setBeerId(beerId)
            BeerDetails(
                beerStateFlow = beerDetailsViewModel.beerDetailsUiModelStateFlow,
                onBack = { beerDetailsViewModel.onScreenTap() },
            )
        }
    }

}

