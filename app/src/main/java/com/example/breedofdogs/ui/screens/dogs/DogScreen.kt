package com.example.breedofdogs.ui.screens.dogs

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.breedofdogs.ui.BREEDS
import com.example.breedofdogs.ui.screens.core.DefaultImageView
import com.example.breedofdogs.ui.screens.core.DefaultToolBarView
import com.example.breedofdogs.ui.screens.core.TagBreed
import com.example.breedofdogs.ui.screens.core.containerTabsAndPage
import com.example.breedofdogs.viewModels.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DogsContainer(
    mainViewModel: MainViewModel = viewModel(),
    navController: NavHostController? = null
) {
    val countFavorites: State<Int> = mainViewModel.onCountFavorite.observeAsState(0)
    DefaultToolBarView("Dogs breeds", navController, countFavorites) {
        CombinedTab(mainViewModel)
    }
}

@ExperimentalPagerApi
@Composable
private fun CombinedTab(mainViewModel: MainViewModel = viewModel()) {
    val pagerState = rememberPagerState(
        pageCount = BREEDS.size,
        initialOffscreenLimit = 1,
        infiniteLoop = true,
        initialPage = 0,
    )
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    containerTabsAndPage(
        tabIndex,
        pagerState,
        contentTabs = {
            BREEDS.forEachIndexed { index, textTab ->
                TagBreed(tabIndex, textTab) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                        mainViewModel.fetchDogsByBreed(BREEDS[index])
                    }
                }
            }
        },
        contentPage = {
            PageDogsByBreed(mainViewModel, it)
        }
    )
}

@Composable
private fun PageDogsByBreed(
    mainViewModel: MainViewModel = viewModel(),
    index: Int
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(0.dp),
        columns = GridCells.Adaptive(200.dp),
    ) {
        val list = mainViewModel.cacheQueries.firstOrNull {
            it.breed == BREEDS[index]
        }
        if (list != null) {
            items(list.dogsImages) {
                DefaultImageView(it) {
                    mainViewModel.addFavoriteCat(it, BREEDS[index])
                }
            }
        } else {
            mainViewModel.updateBreeds(BREEDS[index])
        }
    }
}
