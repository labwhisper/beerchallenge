package com.labwhisper.beerchallenge.beerlist

import com.labwhisper.beerchallenge.beer.Beer
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetBeerListUseCaseTest {

    private val beerListRepository: BeerListRepository = mockk()
    private val dispatcher = StandardTestDispatcher()
    private val sut = GetBeerListUseCase(
        beerListRepository = beerListRepository,
        dispatcher = dispatcher,
    )

    @Test
    fun `Should get the proper beer list from the repository`() = runTest(dispatcher) {
        val beerList: List<Beer> = listOf(mockk())
        coEvery { beerListRepository.getAllBeers(page = 3, perPage = 3) } returns beerList
        val result = sut.getAllBeers(page = 3, perPage = 3)
        assertEquals(beerList, result)
    }
}