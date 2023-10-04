package com.labwhisper.beerchallenge.beer

data class Mashing(
    val temperatureCelsius: Int,
    val durationMinutes: Int // Can be zero (no data in api)
)
