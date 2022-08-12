package com.example.breedofdogs.ui.screens.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@Composable
fun DefaultImageView(
    url: String,
    favorite: Boolean = false,
    addFavorite: () -> Unit = {}
) {
    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 10.dp),
    ) {
        Column {
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
            Row(
                Modifier
                    .background(Color.Black)
                    .fillMaxWidth()
            ) {
                val isFavorite = remember { mutableStateOf(favorite) }
                IconButton(
                    onClick = {
                        isFavorite.value = true
                        addFavorite.invoke()
                    }
                ) {
                    Icon(
                        if (isFavorite.value) Filled.Favorite else Filled.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = Color.Red
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Filled.Share,
                        contentDescription = "Share",
                        tint = Color.Yellow
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun previewDefaultImageView() {
    DefaultImageView("https://sm.ign.com/t/ign_es/screenshot/default/1134207_hnfc.1280.jpg")
}