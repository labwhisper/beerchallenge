package com.labwhisper.beerchallenge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.labwhisper.beerchallenge.beerdetails.BeerDetails
import com.labwhisper.beerchallenge.beerdetails.BeerDetailsViewModel
import com.labwhisper.beerchallenge.beerlist.BeerList
import com.labwhisper.beerchallenge.beerlist.BeerListViewModel

//FIXME DC Replace with Navigator component
@Composable
fun ScreenSwitcher() {

    var currentScreen by remember { mutableStateOf(Screen.List) }
    var selectedItemId by remember { mutableStateOf<Int?>(null) }

    val beerListViewModel: BeerListViewModel = hiltViewModel()
    val beerDetailsViewModel: BeerDetailsViewModel = hiltViewModel()

    when (currentScreen) {
        Screen.List -> BeerList(
            beerItemsStateFlow = beerListViewModel.beerUiModelPageStateFlow,
            onItemClick = { itemId ->
                beerDetailsViewModel.setBeerId(itemId)
                selectedItemId = itemId
                currentScreen = Screen.Detail
            },
        )

        Screen.Detail -> BeerDetails(
            beerStateFlow = beerDetailsViewModel.beerDetailsUiModelStateFlow,
            onBack = {
                currentScreen = Screen.List
                beerDetailsViewModel.setBeerId(null)
            })
    }
}

