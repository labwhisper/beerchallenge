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
    private val beerPagingSource: BeerPagingSource = mockk()
    private val sut = ServerOnlyBeerListRepository(
        beerService = beerService,
        beerPagingSource = beerPagingSource,
        dispatcher = dispatcher
    )

    @Test
    fun `Should get beer by id`() = runTest(dispatcher) {
        val beer: Beer = mockk()
        val beerId = 1
        coEvery { beerService.getBeerById(beerId) } returns beer
        sut.getBeerById(beerId).test {
            assertEquals(beer, awaitItem())
            awaitComplete()
        }
    }
}