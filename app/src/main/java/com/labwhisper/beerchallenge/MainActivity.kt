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
import com.labwhisper.beerchallenge.service.BeerApiResponseMapper
import com.labwhisper.beerchallenge.service.BeerEndpoint
import com.labwhisper.beerchallenge.service.BeerService
import com.labwhisper.beerchallenge.service.ServerOnlyBeerListRepository
import com.labwhisper.beerchallenge.ui.theme.BeerChallengeTheme
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : ComponentActivity() {


    // FIXME DC use dagger
    private val client = OkHttpClient.Builder().build()
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl("https://api.punkapi.com/v2/")
        .client(client)
        .build()
    private val beerEndpoint = retrofit.create(BeerEndpoint::class.java)
    private val beerApiResponseMapper = BeerApiResponseMapper()
    private val beerService = BeerService(
        beerEndpoint = beerEndpoint,
        beerApiResponseMapper = beerApiResponseMapper
    )
    private val beerListRepository by lazy {
        ServerOnlyBeerListRepository(
            dispatcher = Dispatchers.IO,
            beerService = beerService,
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
