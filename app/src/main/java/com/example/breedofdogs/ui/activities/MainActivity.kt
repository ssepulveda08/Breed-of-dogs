package com.example.breedofdogs.ui.activities

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.breedofdogs.ui.screens.HomeSelectionItem
import com.example.breedofdogs.ui.screens.cats.CatsContainer
import com.example.breedofdogs.ui.screens.cats.FavoriteScreenCats
import com.example.breedofdogs.ui.screens.dogs.DogsContainer
import com.example.breedofdogs.ui.screens.dogs.FavoriteScreenDogs
import com.example.breedofdogs.ui.theme.Black
import com.example.breedofdogs.ui.theme.BreedOfDogsTheme
import com.example.breedofdogs.viewModels.CatViewModel
import com.example.breedofdogs.viewModels.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private val catViewModel by viewModels<CatViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BreedOfDogsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().background(Black),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    //val countFavorites by catViewModel.onCountFavorites.observeAsState()
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "HomeSelection") {
                         composable("HomeSelection") { HomeSelectionItem(navController) }
                         composable("Dogs") { DogsContainer(mainViewModel, navController) }
                         composable("FavoriteDogs") { FavoriteScreenDogs(mainViewModel, navController) }
                         composable("Cats") { CatsContainer(catViewModel, navController) }
                        composable("FavoriteCats") { FavoriteScreenCats(catViewModel, navController) }
                    }
                }
            }
        }
    }

    private fun configSyster() {
        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }
}
