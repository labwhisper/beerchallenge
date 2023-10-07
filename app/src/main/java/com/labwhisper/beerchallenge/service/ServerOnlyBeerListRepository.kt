package com.labwhisper.beerchallenge.service

import com.labwhisper.beerchallenge.beer.Beer
import com.labwhisper.beerchallenge.beerlist.BeerListRepository

//FIXME DC Consider an offline support - adding a local repository
class ServerOnlyBeerListRepository(
    private val beerService: BeerService
) : BeerListRepository {

    override suspend fun getAllBeers(page: Int, perPage: Int): List<Beer> {
        return beerService.getBeerList(page = page, perPage = perPage)
    }

    override suspend fun getBeerById(beerId: Int): Beer? =
        beerService.getBeerById(beerId)
}
