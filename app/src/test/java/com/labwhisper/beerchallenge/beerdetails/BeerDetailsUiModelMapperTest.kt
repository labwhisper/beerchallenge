package com.labwhisper.beerchallenge.beerdetails

import com.labwhisper.beerchallenge.beer.Beer
import com.labwhisper.beerchallenge.beer.BrewMethod
import com.labwhisper.beerchallenge.beer.Hop
import com.labwhisper.beerchallenge.beer.Malt
import com.labwhisper.beerchallenge.beer.Mashing
import junit.framework.TestCase
import org.junit.Test

class BeerDetailsUiModelMapperTest {

    private val sut = BeerDetailsUiModelMapper()

    @Test
    fun `Should map a beer to a proper ui model`() {
        val beer = Beer(
            id = 1,
            name = "name",
            imageUrl = "url",
            abv = 1.1,
            description = "description",
            hops = listOf(Hop("hop1"), Hop("hop2")),
            malts = listOf(Malt("malt1"), Malt("malt2")),
            brewMethod = BrewMethod(
                mashing = listOf(
                    Mashing(
                        temperatureCelsius = 1,
                        durationMinutes = 2,
                    )
                ),
                fermentationTemperatureCelsius = 2,
                twist = "twist"
            ),
        )
        val expected = BeerDetailsUiModel.Data(
            id = 1,
            name = "name",
            imageUrl = "url",
            abvString = "Alc. 1.1% Vol.",
            description = "description",
            brewMethodString = "Mashing: 1°C for 2 minutes \nFermentation: 2°C\nTwist: twist",
            hopsString = "Hops: hop1, hop2",
            maltString = "Malts: malt1, malt2",
        )
        val result = sut.mapToDetailsUiModel(beer)
        TestCase.assertEquals(expected, result)
    }
}