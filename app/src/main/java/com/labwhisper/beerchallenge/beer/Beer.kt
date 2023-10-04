package com.labwhisper.beerchallenge.beer


data class Beer(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val abv: Double,
    val description: String,
    val hops: List<Hop>,
    val malts: List<Malt>,
    val brewMethod: BrewMethod
)
