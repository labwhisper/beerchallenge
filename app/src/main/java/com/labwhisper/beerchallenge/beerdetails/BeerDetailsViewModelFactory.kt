package com.labwhisper.beerchallenge.beerdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BeerDetailsViewModelFactory(
    private val getBeerByIdUseCase: GetBeerByIdUseCase,
    private val beerDetailsUiModelMapper: BeerDetailsUiModelMapper,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        BeerDetailsViewModel(getBeerByIdUseCase, beerDetailsUiModelMapper) as T
}