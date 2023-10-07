package com.labwhisper.beerchallenge.beerdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.labwhisper.beerchallenge.navigation.NavigationDestination
import com.labwhisper.beerchallenge.navigation.Navigator
import com.labwhisper.beerchallenge.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class BeerDetailsViewModel @Inject constructor(
    private val getBeerByIdUseCase: GetBeerByIdUseCase,
    private val beerDetailsUiModelMapper: BeerDetailsUiModelMapper,
    private val navigator: Navigator,
) : ViewModel() {

    private val currentBeerId = MutableStateFlow<Int?>(null)

    private val _beerDetailsUiModelFlow: Flow<BeerDetailsUiModel> =
        currentBeerId.flatMapLatest { beerId ->
            if (beerId == null) {
                return@flatMapLatest flowOf(BeerDetailsUiModel.Empty)
            }

            getBeerByIdUseCase.getBeerById(beerId).map { beer ->
                beer?.let(beerDetailsUiModelMapper::mapToDetailsUiModel)
                    ?: BeerDetailsUiModel.Empty
            }
        }

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

    fun onScreenTap() {
        viewModelScope.launch {
            navigator.navigateTo(NavigationDestination(Screen.List))
        }
    }

}
