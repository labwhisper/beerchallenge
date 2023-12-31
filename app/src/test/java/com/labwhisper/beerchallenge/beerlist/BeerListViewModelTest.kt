package com.labwhisper.beerchallenge.beerlist

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
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
class BeerListViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    init {
        Dispatchers.setMain(dispatcher)
    }

    private val beer1: Beer = mockk {
        every { id } returns 1
        every { name } returns "name1"
        every { imageUrl } returns "url1"
        every { abv } returns 1.1
    }
    private val beer2: Beer = mockk {
        every { id } returns 2
        every { name } returns "name2"
        every { imageUrl } returns "url2"
        every { abv } returns 2.2
    }
    private val expectedBeer1 =
        BeerListItemUIModel(id = 1, name = "name1", imageUrl = "url1", abvString = "1.1%")
    private val expectedBeer2 =
        BeerListItemUIModel(id = 2, name = "name2", imageUrl = "url2", abvString = "2.2%")

    private val beerPagingProvider: BeerPagingProvider = mockk()
    private val navigator: Navigator = mockk()
    private val beerListItemUiModelMapper: BeerListItemUiModelMapper = mockk {
        every { map(beer1) } returns expectedBeer1
        every { map(beer2) } returns expectedBeer2
    }

    @Test
    fun `Should be emitted the proper list of beer ui models, when provided by the provider`() =
        runTest(dispatcher) {
            every { beerPagingProvider.getAllBeersPaged(pageSize = 25) } returns
                    flowOf(PagingData.from(listOf(beer1, beer2)))
            val sut = BeerListViewModel(
                beerPagingProvider = beerPagingProvider,
                beerListItemUiModelMapper = beerListItemUiModelMapper,
                navigator = navigator
            )
            advanceUntilIdle()
            sut.beerUiModelPageStateFlow.test {
                assertEquals(listOf(expectedBeer1, expectedBeer2), flowOf(awaitItem()).asSnapshot())
            }
        }

    @Test
    fun `Should navigate to detail screen, when beer is selected`() =
        runTest(dispatcher) {
            every { beerPagingProvider.getAllBeersPaged(pageSize = 25) } returns
                    flowOf(PagingData.from(listOf(beer1, beer2)))
            coJustRun { navigator.navigateTo(any()) }
            val sut = BeerListViewModel(
                beerPagingProvider = beerPagingProvider,
                beerListItemUiModelMapper = beerListItemUiModelMapper,
                navigator = navigator
            )
            sut.onBeerSelected(1)
            advanceUntilIdle()
            coVerify {
                navigator.navigateTo(NavigationDestination(Screen.Detail, mapOf("beerId" to 1)))
            }
        }
}