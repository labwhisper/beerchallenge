package com.labwhisper.beerchallenge.service

import androidx.paging.PagingData
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
        val beerList: PagingData<Beer> = PagingData.from(listOf(mockk(), mockk()))
        every { beerListRepository.getAllBeers() } returns flowOf(beerList)
        sut.getAllBeers().test {
            assertEquals(beerList, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `Should get beer by id`() = runTest {
        val beer1: Beer = mockk {
            every { id } returns 1
        }
        val beer2: Beer = mockk {
            every { id } returns 2
        }
        val beerList: PagingData<Beer> = PagingData.from(listOf(beer1, beer2))
        coEvery { beerListRepository.getAllBeers() } returns flowOf(beerList)
        coEvery { beerListRepository.getBeerById(2) } returns flowOf(beer2)
        sut.getBeerById(2).test {
            assertEquals(beer2, awaitItem())
            awaitComplete()
        }
    }
}