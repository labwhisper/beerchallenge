package com.labwhisper.beerchallenge.service

import com.labwhisper.beerchallenge.beer.Beer
import com.labwhisper.beerchallenge.beerlist.BeerListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

//FIXME DC Consider an offline support - adding a local repository
class ServerOnlyBeerListRepository(
    private val beerService: BeerService,
    private val dispatcher: CoroutineDispatcher
) : BeerListRepository {

    //FIXME DC Consider pagination if more items
    override fun getAllBeers(): Flow<List<Beer>> = flow() {
        emit(beerService.getBeerList())
    }.flowOn(dispatcher)
}