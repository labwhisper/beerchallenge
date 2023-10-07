package com.labwhisper.beerchallenge.beerlist

import com.labwhisper.beerchallenge.beer.Beer

interface BeerListRepository {

    suspend fun getAllBeers(page: Int, perPage: Int): List<Beer>

    suspend fun getBeerById(beerId: Int): Beer?
}