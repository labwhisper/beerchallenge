package com.labwhisper.beerchallenge.beerlist

import com.labwhisper.beerchallenge.beer.Beer

class BeerListItemUiModelMapper {

    fun map(beer: Beer): BeerListItemUIModel = BeerListItemUIModel(
        id = beer.id,
        name = beer.name,
        imageUrl = beer.imageUrl,
        abvString = "Alc. ${beer.abv}% Vol.",
    )
}