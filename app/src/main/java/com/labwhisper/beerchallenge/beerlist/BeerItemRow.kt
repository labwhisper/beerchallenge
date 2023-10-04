package com.labwhisper.beerchallenge.beerlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.labwhisper.beerchallenge.ui.theme.PurpleGrey40
import com.labwhisper.beerchallenge.ui.theme.PurpleGrey80

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
                .size(100.dp)
                .background(PurpleGrey40)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier) {
            Text(text = beer.name, fontWeight = FontWeight.Bold)
            Text(text = beer.abvString, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
@Preview
fun BeerItemRowPreview() {
    BeerItemRow(
        beer = BeerListItemUIModel(
            id = 1,
            name = "Beer 1",
            imageUrl = "someUrl",
            abvString = "1.1%",
        )
    )
}