package com.example.breedofdogs.ui.screens.cats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.breedofdogs.ui.screens.core.DefaultImageView
import com.example.breedofdogs.ui.screens.core.DefaultToolBarView
import com.example.breedofdogs.ui.screens.core.TagBreed
import com.example.breedofdogs.ui.screens.core.containerTabsAndPage
import com.example.breedofdogs.viewModels.CatViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun CatsContainer(viewModel: CatViewModel = viewModel(), navController: NavHostController? = null) {
    val countFavorites: State<Int> = viewModel.onCountFavorites.observeAsState(0)
    DefaultToolBarView(
        "Cat breeds",
        navController,
        countFavorites,
        onClickFavorite = {
            navController?.navigate("FavoriteCats")
        }
    ) {
        if (viewModel.breeds.isNotEmpty()) {
            CombinedTab(viewModel)
        }
    }
}

@ExperimentalPagerApi
@Composable
private fun CombinedTab(viewModel: CatViewModel = viewModel()) {
    val pagerState = rememberPagerState(
        pageCount = viewModel.breeds.size,
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
            viewModel.breeds.forEachIndexed { index, textTab ->
                TagBreed(tabIndex, textTab.name) {
                    coroutineScope.launch {
                        val selectId = viewModel.breeds[index].id
                        pagerState.animateScrollToPage(index)
                        viewModel.fetchCatByBreed(selectId)
                    }
                }
            }
        },
        contentPage = {
            PageCatsByBreed(viewModel, it)
        }
    )
}

@Composable
private fun PageCatsByBreed(
    viewModel: CatViewModel = viewModel(),
    index: Int
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(0.dp),
        columns = GridCells.Adaptive(200.dp),
    ) {
        val list = viewModel.cacheQueries.firstOrNull {
            it.breed == viewModel.breeds[index].id
        }
        if (list != null) {
            items(list.items) {
                DefaultImageView(it.imgUrl, it.isFavorite){
                    viewModel.addFavoriteCat(
                        it.imgUrl,
                        viewModel.breeds[index].name
                    )
                }
            }
        } else {
            val selectId = viewModel.breeds[index].id
            viewModel.updateBreeds(selectId)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Preview(
    name = "Cats Container",
    showBackground = true,
    backgroundColor = 0xFF212121
)
@Composable
private fun PreviewCatsContainer() {
    DefaultToolBarView("Cat breeds") {
        val listPreview = listOf("test1", "test2", "test3")
        val pagerState = rememberPagerState(
            pageCount = listPreview.size,
            initialOffscreenLimit = 1,
            infiniteLoop = true,
            initialPage = 0,
        )
        val tabIndex = pagerState.currentPage
        containerTabsAndPage(
            tabIndex,
            pagerState,
            contentTabs = {
                listPreview.forEachIndexed { index, name ->
                    TagBreed(tabIndex, name) {

                    }
                }
            },
            contentPage = {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)) {

                }
            }
        )
    }
}