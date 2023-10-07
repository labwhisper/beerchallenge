package com.labwhisper.beerchallenge.service

import com.labwhisper.beerchallenge.beer.Beer
import com.labwhisper.beerchallenge.beer.BrewMethod
import com.labwhisper.beerchallenge.beer.Hop
import com.labwhisper.beerchallenge.beer.Malt
import com.labwhisper.beerchallenge.beer.Mashing
import javax.inject.Inject

class BeerApiResponseMapper @Inject constructor() {

    //FIXME DC, need to support units... for now using minutes and Celsius as it seems always the case
    fun map(beerApiResponseModel: BeerApiResponseModel) = Beer(
        id = beerApiResponseModel.id,
        name = beerApiResponseModel.name,
        imageUrl = beerApiResponseModel.imageUrl,
        abv = beerApiResponseModel.abv,
        description = beerApiResponseModel.description,
        hops = beerApiResponseModel.ingredients.hops.map { Hop(it.name) },
        malts = beerApiResponseModel.ingredients.malt.map { Malt(it.name) },
        brewMethod = mapToBrewMethod(beerApiResponseModel.method),
    )

    private fun mapToBrewMethod(beerApiResponseModel: BrewMethodApiResponseModel) = BrewMethod(
        mashing = beerApiResponseModel.mashTemp.map(::mapToMashing),
        fermentationTemperatureCelsius = beerApiResponseModel.fermentation.temp.value?.toInt() ?: 0,
        twist = beerApiResponseModel.twist ?: ""
    )

    private fun mapToMashing(it: MashTempApiResponseModel) = Mashing(
        temperatureCelsius = it.temp.value?.toInt() ?: 0,
        durationMinutes = it.duration ?: 0,
    )

}