package com.labwhisper.beerchallenge.beerlist

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.labwhisper.beerchallenge.beer.Beer
import com.labwhisper.beerchallenge.service.BeerPagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class BeerPagingProvider(
    private val beerPagingSource: BeerPagingSource,
    private val dispatcher: CoroutineDispatcher
) {

    fun getAllBeersPaged(pageSize: Int): Flow<PagingData<Beer>> =
        Pager(PagingConfig(pageSize = pageSize)) {
            beerPagingSource
        }.flow.flowOn(dispatcher)


}