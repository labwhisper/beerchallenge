package com.labwhisper.beerchallenge.di

import com.labwhisper.beerchallenge.beerlist.BeerListRepository
import com.labwhisper.beerchallenge.service.BeerEndpoint
import com.labwhisper.beerchallenge.service.BeerService
import com.labwhisper.beerchallenge.service.ServerOnlyBeerListRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBeerListRepository(beerService: BeerService): BeerListRepository =
        ServerOnlyBeerListRepository(beerService)

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    //FIXME DC add caching (response contains eTag, cache-control)
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://api.punkapi.com/v2/").client(client).build()

    @Singleton
    @Provides
    fun provideBeerEndpoint(retrofit: Retrofit): BeerEndpoint =
        retrofit.create(BeerEndpoint::class.java)

}