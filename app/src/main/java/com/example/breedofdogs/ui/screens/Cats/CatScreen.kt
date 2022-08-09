package com.example.breedofdogs.ui.screens.Cats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.breedofdogs.ui.screens.core.DefaultImageView
import com.example.breedofdogs.ui.screens.core.DefaultToolBarView
import com.example.breedofdogs.ui.theme.Black
import com.example.breedofdogs.ui.theme.BreedOfDogsTheme
import com.example.breedofdogs.viewModels.CatViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CatsContainer(viewModel: CatViewModel = viewModel(), navController: NavHostController? = null) {
    DefaultToolBarView("Cat breeds", navController) {
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
    Column {
        TabBreeds(tabIndex, coroutineScope, pagerState, viewModel)
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PageCatsByBreed(viewModel, index)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TabBreeds(
    tabIndex: Int,
    coroutineScope: CoroutineScope,
    pagerState: PagerState,
    viewModel: CatViewModel = viewModel()
) {
    BreedOfDogsTheme {
        ScrollableTabRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            selectedTabIndex = tabIndex,
            edgePadding = 4.dp,
            containerColor = Black,
            indicator = { tabPositions ->
                Box(
                    Modifier
                        .tabIndicatorOffset(tabPositions[tabIndex])
                        .fillMaxSize()
                        .background(Color.White.copy(alpha = 0.1f))
                )
            }
        ) {
            viewModel.breeds.forEachIndexed { index, textTab ->
                TagBreed(tabIndex, coroutineScope, pagerState, index, viewModel, textTab.name)
            }
        }
    }
}

@Composable
private fun PageCatsByBreed(
    viewModel: CatViewModel = viewModel(),
    index: Int
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(0.dp),
        columns = GridCells.Adaptive(180.dp),
    ) {
        val list = viewModel.cacheQueries.firstOrNull {
            it.breed == viewModel.breeds[index].id
        }
        if (list != null) {
            items(list.dogsImages) {
                DefaultImageView(it)
            }
        } else {
            val selectId = viewModel.breeds[index].id
            viewModel.updateBreeds(selectId)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TagBreed(
    tabIndex: Int,
    coroutineScope: CoroutineScope,
    pagerState: PagerState,
    index: Int,
    viewModel: CatViewModel,
    textTab: String
) {
    Tab(
        modifier = Modifier
            .fillMaxHeight()
            .padding(
                top = 8.dp,
                start = 12.dp,
                end = 12.dp,
                bottom = 8.dp
            ),
        selected = tabIndex == 1,
        onClick = {
            coroutineScope.launch {
                val selectId = viewModel.breeds[index].id
                pagerState.animateScrollToPage(index)
                viewModel.fetchCatByBreed(selectId)
            }
        }) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            text = textTab.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
        )
    }
}