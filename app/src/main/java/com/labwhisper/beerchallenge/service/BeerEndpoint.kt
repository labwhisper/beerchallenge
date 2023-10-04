package com.labwhisper.beerchallenge.service

import retrofit2.Response
import retrofit2.http.GET

interface BeerEndpoint {

    @GET("beers")
    suspend fun getAllBeers(): Response<List<BeerApiResponseModel>>
}
