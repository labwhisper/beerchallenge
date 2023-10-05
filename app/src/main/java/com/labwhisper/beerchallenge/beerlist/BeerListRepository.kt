package com.labwhisper.beerchallenge.beerlist

import androidx.paging.PagingData
import com.labwhisper.beerchallenge.beer.Beer
import kotlinx.coroutines.flow.Flow

interface BeerListRepository {

    fun getAllBeers(): Flow<PagingData<Beer>>

    fun getBeerById(beerId: Int): Flow<Beer?>
}