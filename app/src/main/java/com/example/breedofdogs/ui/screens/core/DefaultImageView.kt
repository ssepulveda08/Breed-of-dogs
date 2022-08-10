package com.example.breedofdogs.ui.screens.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage

@Composable
fun DefaultImageView(url: String) {
    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 10.dp),
    ) {
        SubcomposeAsyncImage(
            model = url,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop,
            loading = {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(80.dp)
                            .padding(24.dp),
                        color = Color.Yellow
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun previewDefaultImageView() {
    DefaultImageView("https://sm.ign.com/t/ign_es/screenshot/default/1134207_hnfc.1280.jpg")
}