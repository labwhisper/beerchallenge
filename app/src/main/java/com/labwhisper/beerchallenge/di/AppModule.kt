package com.labwhisper.beerchallenge.di

import com.labwhisper.beerchallenge.beerlist.BeerListRepository
import com.labwhisper.beerchallenge.service.BeerService
import com.labwhisper.beerchallenge.service.BeerServiceFactory
import com.labwhisper.beerchallenge.service.ServerOnlyBeerListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBeerServiceFactory(): BeerServiceFactory = BeerServiceFactory()

    @Provides
    fun provideBeerService(beerServiceFactory: BeerServiceFactory) = beerServiceFactory.beerService

    @Provides
    fun provideBeerListRepository(beerService: BeerService): BeerListRepository =
        ServerOnlyBeerListRepository(beerService)
}