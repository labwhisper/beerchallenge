package com.labwhisper.beerchallenge.beerlist

import com.labwhisper.beerchallenge.beer.Beer
import com.labwhisper.beerchallenge.beer.BrewMethod
import com.labwhisper.beerchallenge.beer.Hop
import com.labwhisper.beerchallenge.beer.Malt
import com.labwhisper.beerchallenge.beer.Mashing
import com.labwhisper.beerchallenge.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetBeerListUseCase @Inject constructor(
    private val beerListRepository: BeerListRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun getAllBeers(page: Int, perPage: Int): List<Beer> = withContext(dispatcher) {
        beerListRepository.getAllBeers(page = page, perPage = perPage)
    }

    private fun mockBeerList(): List<Beer> = listOf(
        Beer(
            id = 1,
            name = "Hazy Apa",
            imageUrl = "https://picsum.photos/id/1/200/300",
            abv = 1.1,
            description = "Just a beer",
            hops = listOf(Hop("Hop 1")),
            malts = listOf(Malt("Malt 1")),
            brewMethod = BrewMethod(
                mashing = listOf(
                    Mashing(
                        temperatureCelsius = 23,
                        durationMinutes = 60,
                    )
                ),
                fermentationTemperatureCelsius = 40,
                twist = "twist plot"
            ),
        ),
        Beer(
            id = 2,
            name = "IPA",
            imageUrl = "https://picsum.photos/id/2/200/300",
            abv = 2.2,
            description = "Just another beer",
            hops = listOf(Hop("Hop 1"), Hop("Hop 2")),
            malts = listOf(Malt("Malt 2")),
            brewMethod = BrewMethod(
                mashing = listOf(
                    Mashing(
                        temperatureCelsius = 23,
                        durationMinutes = 75,
                    )
                ),
                fermentationTemperatureCelsius = 20,
                twist = "no twist"
            )
        ),
    )

}