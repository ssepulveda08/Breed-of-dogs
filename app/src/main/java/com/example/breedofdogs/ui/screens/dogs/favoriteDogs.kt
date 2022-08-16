package com.example.breedofdogs.ui.screens.dogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells.Adaptive
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.example.breedofdogs.ui.screens.core.DefaultToolBarView
import com.example.breedofdogs.viewModels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreenDogs(
    mainViewModel: MainViewModel = viewModel(),
    navController: NavHostController? = null
) {
    DefaultToolBarView(
        "Favorites",
        navController
    ) {
        LazyVerticalGrid(
            modifier = Modifier.padding(0.dp),
            columns = Adaptive(200.dp),
        ) {
            if (mainViewModel.favorites.isNotEmpty()) {
                items(mainViewModel.favorites) {
                    SubcomposeAsyncImage(
                        model = it.url,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
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
            } else {
                mainViewModel.getFavoriteDogs()
            }
        }
    }
}