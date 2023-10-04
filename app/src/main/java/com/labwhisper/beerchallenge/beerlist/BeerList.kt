@file:OptIn(ExperimentalGlideComposeApi::class)

package com.labwhisper.beerchallenge.beerlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
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
    Column {
        Text(text = "Number of beers: ${beerItems.count()}")
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(
                items = beerItems,
                key = { it.id }
            ) { beer ->
                BeerItemRow(beer)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
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
                    imageUrl = "someUrl",
                    abvString = "1.1%",
                ),
                BeerListItemUIModel(
                    id = 2,
                    name = "Beer 2",
                    imageUrl = "someUrl2",
                    abvString = "2.2%",
                )
            )
        )
    }
}
