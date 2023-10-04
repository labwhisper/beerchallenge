package com.labwhisper.beerchallenge.service

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class BeerServiceFactory {

    val beerService by lazy {
        BeerService(
            beerEndpoint = beerEndpoint,
            beerApiResponseMapper = beerApiResponseMapper
        )
    }

    private val client by lazy { OkHttpClient.Builder().build() }
    private val moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    //FIXME DC add caching (response contains eTag, cache-control)
    private val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://api.punkapi.com/v2/")
            .client(client)
            .build()
    }
    private val beerEndpoint by lazy { retrofit.create(BeerEndpoint::class.java) }
    private val beerApiResponseMapper = BeerApiResponseMapper()
}