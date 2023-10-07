@file:OptIn(ExperimentalCoroutinesApi::class)

package com.labwhisper.beerchallenge.beerdetails

import app.cash.turbine.test
import com.labwhisper.beerchallenge.beer.Beer
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

    private val getBeerByIdUseCase: GetBeerByIdUseCase = mockk {
        every { getBeerById(1) } returns flowOf(beer)
        every { getBeerById(2) } returns flowOf(null)
    }
    private val beerDetailsUiModelMapper: BeerDetailsUiModelMapper = mockk {
        every { mapToDetailsUiModel(beer) } returns beerDetailsUiModel
    }

    @Test
    fun `Should stay in the Empty state when the id is null`() = runTest(dispatcher) {
        val sut = BeerDetailsViewModel(
            getBeerByIdUseCase = getBeerByIdUseCase,
            beerDetailsUiModelMapper = beerDetailsUiModelMapper,
        )
        sut.setBeerId(null)
        advanceUntilIdle()
        sut.beerDetailsUiModelStateFlow.test {
            assertEquals(BeerDetailsUiModel.Empty, awaitItem())
        }
    }

    @Test
    fun `Should stay in the Empty state when the item is not found`() = runTest(dispatcher) {
        val sut = BeerDetailsViewModel(
            getBeerByIdUseCase = getBeerByIdUseCase,
            beerDetailsUiModelMapper = beerDetailsUiModelMapper,
        )
        sut.setBeerId(2)
        advanceUntilIdle()
        sut.beerDetailsUiModelStateFlow.test {
            assertEquals(BeerDetailsUiModel.Empty, awaitItem())
        }
    }

    @Test
    fun `Should have a proper current beer state`() = runTest(dispatcher) {
        val sut = BeerDetailsViewModel(
            getBeerByIdUseCase = getBeerByIdUseCase,
            beerDetailsUiModelMapper = beerDetailsUiModelMapper,
        )
        sut.setBeerId(1)
        advanceUntilIdle()
        sut.beerDetailsUiModelStateFlow.test {
            assertEquals(beerDetailsUiModel, awaitItem())
        }
    }

}