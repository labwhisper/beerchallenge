package com.labwhisper.beerchallenge.beerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BeerListViewModelFactory(
    private val beerPagingProvider: BeerPagingProvider,
    private val beerListItemUiModelMapper: BeerListItemUiModelMapper,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        BeerListViewModel(beerPagingProvider, beerListItemUiModelMapper) as T
}