package com.labwhisper.beerchallenge.beerdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.labwhisper.beerchallenge.beerlist.BeerListProvider

class BeerDetailsViewModelFactory(
    private val beerListProvider: BeerListProvider,
    private val beerDetailsUiModelMapper: BeerDetailsUiModelMapper,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        BeerDetailsViewModel(beerListProvider, beerDetailsUiModelMapper) as T
}