package com.labwhisper.beerchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.labwhisper.beerchallenge.beerdetails.BeerDetailsUiModelMapper
import com.labwhisper.beerchallenge.beerdetails.BeerDetailsViewModelFactory
import com.labwhisper.beerchallenge.beerlist.BeerListItemUiModelMapper
import com.labwhisper.beerchallenge.beerlist.BeerListProvider
import com.labwhisper.beerchallenge.beerlist.BeerListViewModelFactory
import com.labwhisper.beerchallenge.navigation.ScreenSwitcher
import com.labwhisper.beerchallenge.service.BeerPagingSource
import com.labwhisper.beerchallenge.service.BeerServiceFactory
import com.labwhisper.beerchallenge.service.ServerOnlyBeerListRepository
import com.labwhisper.beerchallenge.ui.theme.BeerChallengeTheme
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {


    // FIXME DC use hilt, a factory should be a singleton
    private val beerServiceFactory by lazy { BeerServiceFactory() }

    private val beerService = beerServiceFactory.beerService
    private val beerPagingSource = BeerPagingSource(beerService)
    private val beerListRepository by lazy {
        ServerOnlyBeerListRepository(
            dispatcher = Dispatchers.IO,
            beerService = beerService,
            beerPagingSource = beerPagingSource,
        )
    }
    private val beerListProvider by lazy { BeerListProvider(beerListRepository) }
    private val beerListItemUiModelMapper by lazy { BeerListItemUiModelMapper() }
    private val beerListViewModelFactory by lazy {
        BeerListViewModelFactory(
            beerListProvider = beerListProvider,
            beerListItemUiModelMapper = beerListItemUiModelMapper
        )
    }
    private val beerDetailsUiModelMapper by lazy { BeerDetailsUiModelMapper() }
    private val beerDetailsViewModelFactory by lazy {
        BeerDetailsViewModelFactory(
            beerListProvider = beerListProvider,
            beerDetailsUiModelMapper = beerDetailsUiModelMapper
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BeerChallengeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenSwitcher(
                        beerListViewModelFactory = beerListViewModelFactory,
                        beerDetailsViewModelFactory = beerDetailsViewModelFactory,
                    )
                }
            }
        }
    }
}
