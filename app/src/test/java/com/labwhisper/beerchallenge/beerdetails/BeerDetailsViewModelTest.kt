@file:OptIn(ExperimentalCoroutinesApi::class)

package com.labwhisper.beerchallenge.beerdetails

import app.cash.turbine.test
import com.labwhisper.beerchallenge.beer.Beer
import com.labwhisper.beerchallenge.navigation.NavigationDestination
import com.labwhisper.beerchallenge.navigation.Navigator
import com.labwhisper.beerchallenge.navigation.Screen
import io.mockk.coJustRun
import io.mockk.coVerify
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
    private val navigator: Navigator = mockk()

    @Test
    fun `Should stay in the Empty state when the id is null`() = runTest(dispatcher) {
        val sut = BeerDetailsViewModel(
            getBeerByIdUseCase = getBeerByIdUseCase,
            beerDetailsUiModelMapper = beerDetailsUiModelMapper,
            navigator = navigator,
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
            navigator = navigator,
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
            navigator = navigator,
        )
        sut.setBeerId(1)
        advanceUntilIdle()
        sut.beerDetailsUiModelStateFlow.test {
            assertEquals(beerDetailsUiModel, awaitItem())
        }
    }

    @Test
    fun `Should navigate to the list screen when the screen is tapped`() = runTest(dispatcher) {
        coJustRun { navigator.navigateTo(any()) }
        val sut = BeerDetailsViewModel(
            getBeerByIdUseCase = getBeerByIdUseCase,
            beerDetailsUiModelMapper = beerDetailsUiModelMapper,
            navigator = navigator,
        )
        sut.onScreenTap()
        advanceUntilIdle()
        coVerify { navigator.navigateTo(NavigationDestination(Screen.List)) }
    }

}