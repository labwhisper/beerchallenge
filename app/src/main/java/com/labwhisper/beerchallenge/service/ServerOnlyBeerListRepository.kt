package com.labwhisper.beerchallenge.service

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.labwhisper.beerchallenge.beer.Beer
import com.labwhisper.beerchallenge.beerlist.BeerListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

//FIXME DC Consider an offline support - adding a local repository
class ServerOnlyBeerListRepository(
    private val beerService: BeerService,
    private val beerPagingSource: BeerPagingSource,
    private val dispatcher: CoroutineDispatcher
) : BeerListRepository {

    override fun getAllBeers(): Flow<PagingData<Beer>> {
        return Pager(PagingConfig(pageSize = 25)) {
            beerPagingSource
        }.flow.flowOn(dispatcher)
    }

    override fun getBeerById(beerId: Int): Flow<Beer?> = flow {
        val beer = beerService.getBeerById(beerId)
        emit(beer)
    }.flowOn(dispatcher)
}
