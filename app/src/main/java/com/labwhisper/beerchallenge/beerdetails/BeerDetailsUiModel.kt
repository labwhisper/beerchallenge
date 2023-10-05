package com.labwhisper.beerchallenge.beerdetails

sealed interface BeerDetailsUiModel {

    object Empty : BeerDetailsUiModel
    data class Data(
        val id: Int,
        val name: String,
        val imageUrl: String,
        val abvString: String,
        val description: String,
        val brewMethodString: String,
        val hopsString: String,
        val maltString: String,
    ) : BeerDetailsUiModel
}