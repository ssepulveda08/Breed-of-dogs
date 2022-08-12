package com.example.breedofdogs.ui.screens.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.breedofdogs.ui.theme.Black
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import java.util.Locale

@ExperimentalMaterial3Api
@Composable
fun DefaultToolBarView(
    title: String,
    navController: NavHostController? = null,
    countFavorites: State<Int>? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Black)
            ) {
                val (back, titleToolbar, favorites, count) = createRefs()
                IconButton(
                    modifier = Modifier.constrainAs(back) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, 8.dp)
                    },
                    onClick = { navController?.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Text(
                    text = title,
                    modifier = Modifier
                        .padding(12.dp)
                        .constrainAs(titleToolbar) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(back.end, 12.dp)
                        },
                    style = MaterialTheme.typography.bodyMedium,
                )
                var defaultCount = countFavorites?.value ?: 0
                Text(
                    text = if (defaultCount > 99) {
                        "+99"
                    } else defaultCount.toString(),
                    modifier = Modifier
                        .constrainAs(count) {
                            top.linkTo(favorites.top)
                            bottom.linkTo(favorites.bottom)
                            end.linkTo(favorites.start)
                        }
                        .padding(0.dp),
                    color = Color.White,
                    textAlign = TextAlign.End,
                    fontSize = 10.sp,
                )

                IconButton(
                    modifier = Modifier
                        .constrainAs(favorites) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end, 16.dp)
                        }
                        .padding(0.dp),
                    onClick = { }
                ) {
                    Icon(
                        modifier = Modifier.padding(0.dp),
                        imageVector = if (defaultCount > 0) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "favorites",
                        tint = Color.Red
                    )
                }
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            content.invoke(it)
        }
    }
}

@Composable
fun TabBreeds(
    tabIndex: Int,
    content: @Composable () -> Unit
) {
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
            Divider(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[tabIndex])
                    .padding(start = 8.dp, end = 8.dp),
                color = Color.Yellow
            )
        },
        divider = {}
    ) {
        content.invoke()
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun containerTabsAndPage(
    tabIndex: Int,
    pagerState: PagerState,
    contentTabs: @Composable () -> Unit,
    contentPage: @Composable (page: Int) -> Unit
) {
    Column {
        TabBreeds(tabIndex) {
            contentTabs.invoke()
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                contentPage.invoke(page)
            }
        }
    }
}

@Composable
fun TagBreed(
    tabIndex: Int,
    textTab: String,
    selectTag: () -> Unit
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
        onClick = selectTag
    ) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "ToolBar")
@Composable
private fun PreviewDefaultToolBar() {
    DefaultToolBarView("preView") {

    }
}

@Preview(name = "Tab Breed")
@Composable
private fun PreviewTabBreed() {
    TagBreed(1, "Test Breed") {

    }
}