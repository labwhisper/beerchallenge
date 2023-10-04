package com.labwhisper.beerchallenge.beerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BeerListViewModelFactory(
    private val beerListProvider: BeerListProvider,
    private val beerListItemUiModelMapper: BeerListItemUiModelMapper
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        BeerListViewModel(beerListProvider, beerListItemUiModelMapper) as T
}