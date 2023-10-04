package com.labwhisper.beerchallenge.beerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BeerListViewModel(
    beerListProvider: BeerListProvider,
    beerListItemUiModelMapper: BeerListItemUiModelMapper,
) : ViewModel() {

    private val _beerUiModelListFlow: Flow<List<BeerListItemUIModel>> =
        beerListProvider.getAllBeers().map { beers ->
            beers.map(beerListItemUiModelMapper::map)
        }

    val beerUiModelListStateFlow: StateFlow<List<BeerListItemUIModel>> =
        _beerUiModelListFlow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList(),
        )

}
