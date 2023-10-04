package com.labwhisper.beerchallenge.service

import com.labwhisper.beerchallenge.beer.Beer
import com.labwhisper.beerchallenge.beer.BrewMethod
import com.labwhisper.beerchallenge.beer.Hop
import com.labwhisper.beerchallenge.beer.Malt
import com.labwhisper.beerchallenge.beer.Mashing
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Test

class BeerApiResponseMapperTest {

    private val sut = BeerApiResponseMapper()

    private val beerApiResponseModel = BeerApiResponseModel(
        id = 1,
        name = "name",
        imageUrl = "imageUrl",
        description = "description",
        method = BrewMethodApiResponseModel(
            mashTemp = listOf(
                MashTempApiResponseModel(
                    temp = AmountApiResponseModel(
                        value = 1.0,
                        unit = "unit"
                    ),
                    duration = 1
                )
            ),
            fermentation = FermentationApiResponseModel(
                temp = AmountApiResponseModel(
                    value = 1.0,
                    unit = "unit"
                )
            ),
            twist = "twist"
        ),
        ingredients = IngredientsApiResponseModel(
            hops = listOf(
                HopApiResponseModel(
                    name = "hopName",
                    amount = AmountApiResponseModel(
                        value = 1.0,
                        unit = "unit"
                    ),
                    add = "add",
                    attribute = "attribute"
                )
            ),
            malt = listOf(
                MaltApiResponseModel(
                    name = "maltName",
                    amount = AmountApiResponseModel(
                        value = 1.0,
                        unit = "unit"
                    )
                )
            )
        ),
    )
    private val beer = Beer(
        id = 1,
        name = "name",
        imageUrl = "imageUrl",
        description = "description",
        brewMethod = BrewMethod(
            mashing = listOf(
                Mashing(
                    temperatureCelsius = 1,
                    durationMinutes = 1
                )
            ),
            fermentationTemperatureCelsius = 1,
            twist = "twist"
        ),
        hops = listOf(
            Hop(
                name = "hopName"
            )
        ),
        malts = listOf(
            Malt(
                name = "maltName"
            )
        )
    )

    @Test
    fun `Should map beer response to beer`() {
        val result = sut.map(beerApiResponseModel)
        assertEquals(beer, result)
    }

    @Test
    fun `Should set duration to zero on null duration field`() {
        val beerApiResponseModel: BeerApiResponseModel = mockk(relaxed = true) {
            every { method } returns mockk(relaxed = true) {
                every { mashTemp } returns listOf(
                    MashTempApiResponseModel(
                        temp = AmountApiResponseModel(
                            value = 1.0,
                            unit = "unit"
                        ),
                        duration = null
                    )
                )
            }
        }
        assertEquals(0, sut.map(beerApiResponseModel).brewMethod.mashing[0].durationMinutes)
    }

    @Test
    fun `Should set twist to empty string on null twist`() {
        val beerApiResponseModel: BeerApiResponseModel = mockk(relaxed = true) {
            every { method } returns mockk(relaxed = true) {
                every { twist } returns null
            }
        }
        assertEquals("", sut.map(beerApiResponseModel).brewMethod.twist)
    }

}
