package com.iamvishnu.samples.flowing.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    onDetails: (String) -> Unit
) {

    Column {

        Text(text = "Home")

        Button(
            onClick = { onDetails("1234abcd") }
        ) {
            Text("Show Details Screen")
        }

    }

}