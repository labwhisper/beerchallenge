package com.labwhisper.beerchallenge.beerlist

import com.labwhisper.beerchallenge.beer.Beer
import com.labwhisper.beerchallenge.beer.BrewMethod
import com.labwhisper.beerchallenge.beer.Hop
import com.labwhisper.beerchallenge.beer.Malt
import com.labwhisper.beerchallenge.beer.Mashing
import junit.framework.TestCase.assertEquals
import org.junit.Test

class BeerListItemUiModelMapperTest {

    private val sut = BeerListItemUiModelMapper()

    @Test
    fun `Should map the beer to the proper ui model`() {
        val beer = Beer(
            id = 1,
            name = "name",
            imageUrl = "url",
            description = "description",
            hops = listOf(Hop("hop1")),
            malts = listOf(Malt("malt1")),
            brewMethod = BrewMethod(
                mashing = listOf(
                    Mashing(
                        temperatureCelsius = 1,
                        durationMinutes = 1,
                    )
                ),
                fermentationTemperatureCelsius = 2,
                twist = "twist"
            ),
        )
        val expected = BeerListItemUIModel(
            id = 1,
            name = "name",
            imageUrl = "url",
        )
        val result = sut.map(beer)
        assertEquals(expected, result)
    }
}