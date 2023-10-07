package com.labwhisper.beerchallenge.beerdetails

import com.labwhisper.beerchallenge.beer.Beer
import com.labwhisper.beerchallenge.beerlist.BeerListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetBeerByIdUseCase(
    private val beerListRepository: BeerListRepository,
    private val dispatcher: CoroutineDispatcher,
) {

    fun getBeerById(id: Int): Flow<Beer?> = flow {
        emit(beerListRepository.getBeerById(id))
    }.flowOn(dispatcher)
}
