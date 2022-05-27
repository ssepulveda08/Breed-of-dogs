@file:OptIn(ExperimentalPagerApi::class)

package com.example.breedofdogs.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.breedofdogs.ui.BREEDS
import com.example.breedofdogs.ui.theme.Black
import com.example.breedofdogs.ui.theme.BreedOfDogsTheme
import com.example.breedofdogs.viewModels.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BreedOfDogsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().background(Black),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    ToolBarContainer(mainViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToolBarContainer(mainViewModel: MainViewModel = viewModel()) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Black)
            ) {
                Text(
                    text = "Dog breeds",
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            CombinedTab(mainViewModel)
        }
    }
}

@ExperimentalPagerApi
@Composable
fun CombinedTab(mainViewModel: MainViewModel = viewModel()) {
    val pagerState = rememberPagerState(
        pageCount = BREEDS.size,
        initialOffscreenLimit = 1,
        infiniteLoop = true,
        initialPage = 0,
    )
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    Column {
        TabBreeds(tabIndex, coroutineScope, pagerState, mainViewModel)
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PageDogsByBreed(mainViewModel, index)
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
    mainViewModel: MainViewModel = viewModel()
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
            BREEDS.forEachIndexed { index, textTab ->
                TagBreed(tabIndex, coroutineScope, pagerState, index, mainViewModel, textTab)
            }
        }
    }
}

@Composable
private fun PageDogsByBreed(
    mainViewModel: MainViewModel = viewModel(),
    index: Int
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(0.dp),
        columns = GridCells.Adaptive(180.dp),
    ) {
        val list = mainViewModel.cacheQueries.firstOrNull {
            it.breed == BREEDS[index]
        }
        if (list != null) {
            items(list.dogsImages) {
                ImageDog(it)
            }
        } else {
            mainViewModel.updateBreeds(BREEDS[index])
        }
    }
}

@Composable
private fun ImageDog(it: String) {
    Column {
        AsyncImage(
            model = it,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TagBreed(
    tabIndex: Int,
    coroutineScope: CoroutineScope,
    pagerState: PagerState,
    index: Int,
    mainViewModel: MainViewModel,
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
                pagerState.animateScrollToPage(index)
                mainViewModel.fetchDogsByBreed(BREEDS[index])
            }
        }) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            text = textTab.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        )
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BreedOfDogsTheme {
        Greeting("Android")
    }
}