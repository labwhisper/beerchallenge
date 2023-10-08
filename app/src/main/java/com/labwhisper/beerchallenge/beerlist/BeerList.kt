package com.labwhisper.beerchallenge.beerlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.labwhisper.beerchallenge.ui.theme.BeerChallengeTheme
import com.labwhisper.beerchallenge.ui.theme.Purple90
import com.labwhisper.beerchallenge.ui.theme.PurpleGrey40
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf

@Composable
fun BeerList(
    beerItemsStateFlow: StateFlow<PagingData<BeerListItemUIModel>>,
    onItemClick: (beerId: Int) -> Unit,
) {
    val beerItems: LazyPagingItems<BeerListItemUIModel> =
        beerItemsStateFlow.collectAsLazyPagingItems()
    BeerListContent(
        beerItems = beerItems,
        onItemClick = onItemClick,
    )
}

@Composable
fun BeerListContent(
    beerItems: LazyPagingItems<BeerListItemUIModel>,
    onItemClick: (beerId: Int) -> Unit,
) {
    Column(modifier = Modifier.background(PurpleGrey40)) {
        Text(
            text = "Brews list",
            color = Purple90,
            fontSize = 40.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(
                count = beerItems.itemCount,
                key = beerItems.itemKey { it.id },
            ) { index ->
                val beer: BeerListItemUIModel = beerItems[index] ?: return@items
                BeerItemRow(
                    modifier = Modifier.clickable { onItemClick(beer.id) },
                    beer,
                )
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
            beerItems = flowOf(
                PagingData.from(
                    listOf(
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
            ).collectAsLazyPagingItems(),
            onItemClick = { },
        )
    }
}
