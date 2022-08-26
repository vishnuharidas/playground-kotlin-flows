package com.iamvishnu.samples.flowing.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iamvishnu.samples.flowing.data.model.DummyPost
import com.iamvishnu.samples.flowing.ui.common.PostItem

@Composable
fun HomeScreen(
    onDetails: (String) -> Unit,
) {

    val vm: HomeViewModel = viewModel()

    Column {

        Text(text = "Home")

        when (vm.state.value) {

            HomeUiState.Loading -> CircularProgressIndicator()

            HomeUiState.None -> Text("Nothing here")

            is HomeUiState.Success -> ListItems(
                vm.itemsList,
                onLike = { vm.likePost(it) },
                onShare = { vm.sharePost(it) }
            ) {
                onDetails(it)
            }
        }

    }

}

@Composable
private fun ListItems(
    list: List<DummyPost>,
    onLike: (String) -> Unit,
    onShare: (String) -> Unit,
    onDetails: (String) -> Unit,
) {

    LazyColumn {

        items(list) {

            PostItem(
                post = it,
                onLike = onLike,
                onShare = onShare,
                onDetails = onDetails
            )

            Divider(color = Color.Black)
        }
    }

}