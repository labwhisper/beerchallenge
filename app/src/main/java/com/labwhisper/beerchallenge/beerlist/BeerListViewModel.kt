package com.labwhisper.beerchallenge.beerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BeerListViewModel @Inject constructor(
    beerPagingProvider: BeerPagingProvider,
    beerListItemUiModelMapper: BeerListItemUiModelMapper,
) : ViewModel() {

    private val _beerUiModelPageFlow: Flow<PagingData<BeerListItemUIModel>> =
        beerPagingProvider.getAllBeersPaged(pageSize = 25)
            .map { beersPage -> beersPage.map(beerListItemUiModelMapper::map) }
            .cachedIn(viewModelScope)

    val beerUiModelPageStateFlow: StateFlow<PagingData<BeerListItemUIModel>> =
        _beerUiModelPageFlow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = PagingData.empty(),
        )

}
