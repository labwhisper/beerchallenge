@file:OptIn(ExperimentalGlideComposeApi::class)

package com.labwhisper.beerchallenge.beerdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.labwhisper.beerchallenge.ui.theme.PurpleGrey40
import kotlinx.coroutines.flow.StateFlow


@Composable
fun BeerDetails(beerStateFlow: StateFlow<BeerDetailsUiModel>, onBack: () -> Unit) {

    val beerDetailsUiModel: BeerDetailsUiModel by beerStateFlow.collectAsState(initial = BeerDetailsUiModel.Empty)
    BeerDetailsContent(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onBack() },
        beerDetailsUiModel = beerDetailsUiModel
    )

}

@Composable
fun BeerDetailsContent(
    modifier: Modifier = Modifier,
    beerDetailsUiModel: BeerDetailsUiModel
) {
    Box(modifier = modifier) {
        BeerDetailsImage(beerDetailsUiModel = beerDetailsUiModel)
        BeerDetailsTexts(beerDetailsUiModel = beerDetailsUiModel)
    }

}

@Composable
fun BeerDetailsTexts(beerDetailsUiModel: BeerDetailsUiModel) {
    when (beerDetailsUiModel) {
        is BeerDetailsUiModel.Data ->
            Column(modifier = Modifier) {
                Spacer(modifier = Modifier.size(16.dp))
                HeaderLine(beerDetailsUiModel.name)
                Spacer(modifier = Modifier.size(8.dp))
                DetailsLine(beerDetailsUiModel.description)
                Spacer(modifier = Modifier.size(8.dp))
                ItemsLine(beerDetailsUiModel.abvString)
                Spacer(modifier = Modifier.size(8.dp))
                ItemsLine(beerDetailsUiModel.brewMethodString)
                Spacer(modifier = Modifier.size(8.dp))
                ItemsLine(beerDetailsUiModel.hopsString)
                Spacer(modifier = Modifier.size(8.dp))
                ItemsLine(beerDetailsUiModel.maltString)
                Spacer(modifier = Modifier.size(16.dp))
            }

        BeerDetailsUiModel.Empty -> Text(text = "Nothing to show")
    }
}

@Composable
private fun HeaderLine(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 40.sp
    )
}

@Composable
private fun DetailsLine(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 16.sp
    )
}

@Composable
private fun ItemsLine(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 24.sp
    )
}

@Composable
fun BeerDetailsImage(beerDetailsUiModel: BeerDetailsUiModel) {
    when (beerDetailsUiModel) {
        is BeerDetailsUiModel.Data -> Box(modifier = Modifier) {
            GlideImage(
                model = beerDetailsUiModel.imageUrl,
                contentDescription = "Beer details image",
                modifier = Modifier
                    .fillMaxSize()
                    .background(PurpleGrey40)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.6f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }

        BeerDetailsUiModel.Empty -> Unit
    }

}
