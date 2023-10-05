package com.labwhisper.beerchallenge.beerdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.labwhisper.beerchallenge.beerlist.BeerListProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class BeerDetailsViewModel(
    private val beerListProvider: BeerListProvider,
    private val beerDetailsUiModelMapper: BeerDetailsUiModelMapper,
) : ViewModel() {

    private val currentBeerId = MutableStateFlow<Int?>(null)

    private val _beerDetailsUiModelFlow: Flow<BeerDetailsUiModel> =
        currentBeerId.flatMapLatest { beerId ->
            beerId?.let { beerListProvider.getBeerById(it) }
                ?.map(beerDetailsUiModelMapper::mapToDetailsUiModel)
                ?: flowOf(BeerDetailsUiModel.Empty)
        }.flowOn(Dispatchers.Default)

    val beerDetailsUiModelStateFlow: StateFlow<BeerDetailsUiModel> =
        _beerDetailsUiModelFlow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = BeerDetailsUiModel.Empty,
        )

    fun setBeerId(id: Int?) {
        viewModelScope.launch {
            currentBeerId.emit(id)

        }
    }

}
