package com.labwhisper.beerchallenge.beerlist

import com.labwhisper.beerchallenge.beer.Beer
import com.labwhisper.beerchallenge.beer.BrewMethod
import com.labwhisper.beerchallenge.beer.Hop
import com.labwhisper.beerchallenge.beer.Malt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class BeerListProvider {

    fun getAllBeers(): Flow<List<Beer>> {
        return flowOf(mockBeerList())
    }

    private fun mockBeerList(): List<Beer> = listOf(
        Beer(
            id = 1,
            name = "Hazy Apa",
            imageUrl = "https://picsum.photos/seed/picsum/200/300",
            description = "Just a beer",
            hops = listOf(Hop("Hop 1")),
            malts = listOf(Malt("Malt 1")),
            brewMethod = BrewMethod(
                mashTemperatureCelsius = 23,
                fermentationTemperatureCelsius = 40,
                twist = "twist plot"
            ),
        ),
        Beer(
            id = 2,
            name = "IPA",
            imageUrl = "https://picsum.photos/seed/picsum/200/300",
            description = "Just another beer",
            hops = listOf(Hop("Hop 1"), Hop("Hop 2")),
            malts = listOf(Malt("Malt 2")),
            brewMethod = BrewMethod(
                mashTemperatureCelsius = 10,
                fermentationTemperatureCelsius = 20,
                twist = "no twist"
            )

        ),
    )

}