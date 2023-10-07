package com.labwhisper.beerchallenge.beerdetails

import app.cash.turbine.test
import com.labwhisper.beerchallenge.beer.Beer
import com.labwhisper.beerchallenge.beerlist.BeerListRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetBeerByIdUseCaseTest {

    private val beerListRepository: BeerListRepository = mockk()
    private val dispatcher = StandardTestDispatcher()
    private val sut = GetBeerByIdUseCase(
        beerListRepository = beerListRepository,
        dispatcher = dispatcher,
    )

    @Test
    fun `Should get the proper beer from the repository`() = runTest {
        val beer: Beer = mockk()
        coEvery { beerListRepository.getBeerById(1) } returns beer
        sut.getBeerById(1).test {
            assertEquals(beer, awaitItem())
            awaitComplete()
        }
    }
}