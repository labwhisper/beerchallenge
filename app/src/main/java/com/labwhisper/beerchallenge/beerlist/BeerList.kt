@file:OptIn(ExperimentalGlideComposeApi::class)

package com.labwhisper.beerchallenge.beerlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.labwhisper.beerchallenge.ui.theme.BeerChallengeTheme
import com.labwhisper.beerchallenge.ui.theme.PurpleGrey40
import com.labwhisper.beerchallenge.ui.theme.PurpleGrey80
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BeerItemRow(beer: BeerListItemUIModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(PurpleGrey80)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = beer.imageUrl,
            contentDescription = "Beer image",
            modifier = Modifier
                .size(50.dp)
                .background(PurpleGrey40)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = beer.name, fontWeight = FontWeight.Medium)
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
