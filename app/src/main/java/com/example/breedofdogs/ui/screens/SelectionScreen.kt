package com.example.breedofdogs.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.breedofdogs.R

@Composable
fun HomeSelectionItem(navController: NavHostController? = null) {
    OrientationView(
        composableLandscape = {
            LandscapeContainer(navController)
        },
        composablePortrait = {
            PortraitContainer(navController)
        }
    )
}

@Composable
private fun LandscapeContainer(navController: NavHostController? = null) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ContainerButtons(
            navController,
            Modifier.padding(start = 24.dp)
        )
    }
}

@Composable
private fun PortraitContainer(navController: NavHostController? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContainerButtons(
            navController,
            Modifier.padding(top = 24.dp)
        )
    }
}

@Composable
private fun ContainerButtons(navController: NavHostController? = null, buttonModifier: Modifier = Modifier) {
    ButtonSelection("Dogs", R.drawable.hueso, buttonModifier) {
        navController?.navigate("Dogs")
    }

    ButtonSelection("Cats", R.drawable.smile, buttonModifier) {
        navController?.navigate("Cats")
    }
}

@Composable
private fun ButtonSelection(name: String, icon: Int, buttonModifier: Modifier, action: () -> Unit) {
    Button(
        onClick = action,
        shape = RoundedCornerShape(10.dp),
        modifier = buttonModifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Yellow,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier.size(200.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = name,
                modifier = Modifier
                    .padding(8.dp)
                    .size(140.dp)
            )
            Text(
                text = name,
                fontSize = 18.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
fun ComposablePreview() {
    HomeSelectionItem()
}