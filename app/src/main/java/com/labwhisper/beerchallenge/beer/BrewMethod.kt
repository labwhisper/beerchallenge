package com.labwhisper.beerchallenge.beer

data class BrewMethod(
    val mashing: List<Mashing>,
    val fermentationTemperatureCelsius: Int,
    val twist: String,
)
