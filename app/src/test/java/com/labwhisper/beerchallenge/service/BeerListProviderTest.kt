package com.labwhisper.beerchallenge.service

import app.cash.turbine.test
import com.labwhisper.beerchallenge.beer.Beer
import com.labwhisper.beerchallenge.beerlist.BeerListProvider
import com.labwhisper.beerchallenge.beerlist.BeerListRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class BeerListProviderTest {

    private val beerListRepository: BeerListRepository = mockk()
    private val sut = BeerListProvider(
        beerListRepository = beerListRepository,
    )

    @Test
    fun `Should get all beers`() = runTest {
        val beerList: List<Beer> = listOf(mockk(), mockk())
        every { beerListRepository.getAllBeers() } returns flowOf(beerList)
        sut.getAllBeers().test {
            assertEquals(beerList, awaitItem())
            awaitComplete()
        }
    }
}