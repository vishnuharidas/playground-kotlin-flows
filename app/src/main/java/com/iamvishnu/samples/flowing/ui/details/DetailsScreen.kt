package com.iamvishnu.samples.flowing.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun DetailsScreen(
    id: String
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Details of $id"
        )
    }
}