package com.labwhisper.beerchallenge.beerdetails

import com.labwhisper.beerchallenge.beer.Beer
import com.labwhisper.beerchallenge.beer.BrewMethod
import com.labwhisper.beerchallenge.beer.Hop
import com.labwhisper.beerchallenge.beer.Malt
import com.labwhisper.beerchallenge.beer.Mashing
import javax.inject.Inject

class BeerDetailsUiModelMapper @Inject constructor() {
    fun mapToDetailsUiModel(beer: Beer) = BeerDetailsUiModel.Data(
        id = beer.id,
        name = beer.name,
        imageUrl = beer.imageUrl,
        abvString = "Alc. ${beer.abv}% Vol.",
        description = beer.description,
        brewMethodString = mapToBrewMethodString(beer.brewMethod),
        hopsString = mapToHopString(beer.hops),
        maltString = mapToMaltString(beer.malts)
    )

    private fun mapToMaltString(maltList: List<Malt>): String {
        return maltList.joinToString(separator = ", ", prefix = "Malts: ") { it.name }
    }

    private fun mapToHopString(hops: List<Hop>): String {
        return hops.joinToString(separator = ", ", prefix = "Hops: ") { it.name }
    }

    private fun mapToBrewMethodString(brewMethod: BrewMethod): String {
        return "Mashing: " + mapToMashingString(brewMethod.mashing) + " \n" +
                "Fermentation: ${brewMethod.fermentationTemperatureCelsius}°C\n" +
                "Twist: ${brewMethod.twist}"
    }

    private fun mapToMashingString(mashing: List<Mashing>) =
        mashing.joinToString(separator = ", ") {
            "${it.temperatureCelsius}°C for ${it.durationMinutes} minutes"
        }
}