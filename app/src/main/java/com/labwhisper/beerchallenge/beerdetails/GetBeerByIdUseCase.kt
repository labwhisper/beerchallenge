package com.labwhisper.beerchallenge.beerdetails

import com.labwhisper.beerchallenge.beer.Beer
import com.labwhisper.beerchallenge.beerlist.BeerListRepository
import com.labwhisper.beerchallenge.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetBeerByIdUseCase @Inject constructor(
    private val beerListRepository: BeerListRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) {

    fun getBeerById(id: Int): Flow<Beer?> = flow {
        emit(beerListRepository.getBeerById(id))
    }.flowOn(dispatcher)
}
