package com.labwhisper.beerchallenge.beerlist

import com.labwhisper.beerchallenge.beer.Beer
import kotlinx.coroutines.flow.Flow

interface BeerListRepository {

    fun getAllBeers(): Flow<List<Beer>>
}