package com.labwhisper.beerchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.labwhisper.beerchallenge.beerlist.BeerList
import com.labwhisper.beerchallenge.beerlist.BeerListItemUiModelMapper
import com.labwhisper.beerchallenge.beerlist.BeerListProvider
import com.labwhisper.beerchallenge.beerlist.BeerListViewModel
import com.labwhisper.beerchallenge.beerlist.BeerListViewModelFactory
import com.labwhisper.beerchallenge.ui.theme.BeerChallengeTheme

class MainActivity : ComponentActivity() {

    // FIXME DC use dagger if problematic
    private val beerListProvider by lazy { BeerListProvider() }
    private val beerListItemUiModelMapper by lazy { BeerListItemUiModelMapper() }
    private val beerListViewModelFactory by lazy {
        BeerListViewModelFactory(
            beerListProvider = beerListProvider,
            beerListItemUiModelMapper = beerListItemUiModelMapper
        )
    }
    private val viewModel: BeerListViewModel by viewModels { beerListViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BeerChallengeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BeerList(beerItemsStateFlow = viewModel.beerUiModelListStateFlow)
                }
            }
        }
    }
}
