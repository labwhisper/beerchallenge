package com.labwhisper.beerchallenge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.labwhisper.beerchallenge.beerdetails.BeerDetails
import com.labwhisper.beerchallenge.beerdetails.BeerDetailsViewModel
import com.labwhisper.beerchallenge.beerdetails.BeerDetailsViewModelFactory
import com.labwhisper.beerchallenge.beerlist.BeerList
import com.labwhisper.beerchallenge.beerlist.BeerListViewModel
import com.labwhisper.beerchallenge.beerlist.BeerListViewModelFactory

//FIXME DC Replace with Navigator component
@Composable
fun ScreenSwitcher(
    beerListViewModelFactory: BeerListViewModelFactory,
    beerDetailsViewModelFactory: BeerDetailsViewModelFactory,
) {

    var currentScreen by remember { mutableStateOf(Screen.List) }
    var selectedItemId by remember { mutableStateOf<Int?>(null) }

    val beerListViewModel = viewModel<BeerListViewModel>(factory = beerListViewModelFactory)
    val beerDetailsViewModel =
        viewModel<BeerDetailsViewModel>(factory = beerDetailsViewModelFactory)

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

