package com.labwhisper.beerchallenge.service

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BeerApiResponseModel(
    val id: Int,
    val name: String,
    @Json(name = "image_url") val imageUrl: String,
    val abv: Double,
    val description: String,
    val ingredients: IngredientsApiResponseModel,
    val method: BrewMethodApiResponseModel
)

@JsonClass(generateAdapter = true)
data class IngredientsApiResponseModel(
    val hops: List<HopApiResponseModel>,
    val malt: List<MaltApiResponseModel>,
)

@JsonClass(generateAdapter = true)
data class BrewMethodApiResponseModel(
    @Json(name = "mash_temp") val mashTemp: List<MashTempApiResponseModel>,
    val fermentation: FermentationApiResponseModel,
    val twist: String?,
)

@JsonClass(generateAdapter = true)
data class MashTempApiResponseModel(
    val temp: AmountApiResponseModel,
    val duration: Int?,
)

@JsonClass(generateAdapter = true)
data class FermentationApiResponseModel(
    val temp: AmountApiResponseModel,
)

@JsonClass(generateAdapter = true)
data class HopApiResponseModel(
    val name: String,
    val amount: AmountApiResponseModel,
    val add: String,
    val attribute: String,
)

@JsonClass(generateAdapter = true)
data class MaltApiResponseModel(
    val name: String,
    val amount: AmountApiResponseModel,
)

@JsonClass(generateAdapter = true)
data class AmountApiResponseModel(
    val value: Double?,
    val unit: String,
)
