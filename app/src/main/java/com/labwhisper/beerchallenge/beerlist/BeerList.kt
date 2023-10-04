package com.labwhisper.beerchallenge.beerlist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.labwhisper.beerchallenge.ui.theme.BeerChallengeTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun BeerList(beerItemsStateFlow: StateFlow<List<BeerListItemUIModel>>) {
    val beerItems: List<BeerListItemUIModel> by beerItemsStateFlow.collectAsState(initial = emptyList())
    BeerListContent(
        beerItems = beerItems
    )
}

@Composable
fun BeerListContent(beerItems: List<BeerListItemUIModel>) {
    Text(text = "Number of beers: ${beerItems.count()}")
}

@Preview(showBackground = true)
@Composable
fun BeerListContentPreview() {
    BeerChallengeTheme {
        BeerListContent(
            beerItems = listOf(
                BeerListItemUIModel(
                    id = 1,
                    name = "Beer 1",
                    imageUrl = "someUrl"
                ),
                BeerListItemUIModel(
                    id = 2,
                    name = "Beer 2",
                    imageUrl = "someUrl2"
                )
            )
        )
    }
}
