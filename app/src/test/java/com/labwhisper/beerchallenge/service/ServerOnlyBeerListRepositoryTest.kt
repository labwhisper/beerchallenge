package com.labwhisper.beerchallenge.service

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
    private val sut = ServerOnlyBeerListRepository(beerService = beerService)

    @Test
    fun `Should get all beers`() = runTest(dispatcher) {
        val beerList: List<Beer> = listOf(mockk(), mockk())
        coEvery { beerService.getBeerList(page = 3, perPage = 4) } returns beerList
        val result = sut.getAllBeers(page = 3, perPage = 4)
        assertEquals(beerList, result)
    }

    @Test
    fun `Should get beer by id`() = runTest(dispatcher) {
        val beer: Beer = mockk()
        val beerId = 1
        coEvery { beerService.getBeerById(beerId) } returns beer
        val result = sut.getBeerById(beerId)
        assertEquals(beer, result)
    }
}