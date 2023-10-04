package com.labwhisper.beerchallenge.service

import app.cash.turbine.test
import com.labwhisper.beerchallenge.beer.Beer
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ServerOnlyBeerListRepositoryTest {

    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()
    private val beerService: BeerService = mockk()
    private val sut = ServerOnlyBeerListRepository(
        beerService = beerService,
        dispatcher = dispatcher
    )

    @Test
    fun `Should get all beers`() = runTest(dispatcher) {
        val beerList: List<Beer> = listOf(mockk(), mockk())
        coEvery { beerService.getBeerList() } returns beerList
        sut.getAllBeers().test {
            assertEquals(beerList, awaitItem())
            awaitComplete()
        }
    }
}