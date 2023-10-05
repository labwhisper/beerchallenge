@file:OptIn(ExperimentalCoroutinesApi::class)

package com.labwhisper.beerchallenge.beerdetails

import app.cash.turbine.test
import com.labwhisper.beerchallenge.beer.Beer
import com.labwhisper.beerchallenge.beerlist.BeerListProvider
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BeerDetailsViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    init {
        Dispatchers.setMain(dispatcher)
    }

    private val beer: Beer = mockk()
    private val beerDetailsUiModel: BeerDetailsUiModel.Data = mockk()

    private val beerListProvider: BeerListProvider = mockk {
        every { getBeerById(1) } returns flowOf(beer)
    }
    private val beerDetailsUiModelMapper: BeerDetailsUiModelMapper = mockk {
        every { mapToDetailsUiModel(beer) } returns beerDetailsUiModel
    }

    @Test
    fun `Should have a proper current beer state`() = runTest(dispatcher) {
        val sut = BeerDetailsViewModel(
            beerListProvider = beerListProvider,
            beerDetailsUiModelMapper = beerDetailsUiModelMapper,
        )
        sut.setBeerId(1)
        sut.beerDetailsUiModelStateFlow.test {
            advanceUntilIdle()
            assertEquals(BeerDetailsUiModel.Empty, awaitItem())
        }
    }
}