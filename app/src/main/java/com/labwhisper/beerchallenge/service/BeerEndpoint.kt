package com.labwhisper.beerchallenge.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BeerEndpoint {

    @GET("beers")
    suspend fun getAllBeers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<List<BeerApiResponseModel>>

    @GET("beers/{id}")
    suspend fun getBeerById(@Path("id") id: Int): Response<List<BeerApiResponseModel>>
}
