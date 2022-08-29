package com.iamvishnu.samples.flowing.ui.details

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iamvishnu.samples.flowing.ui.common.PostItem

@Composable
fun DetailsScreen(
    id: String
) {

    val vm: DetailsViewModel = viewModel()

    LaunchedEffect(vm, id) {
        vm.getPost(id)
    }

    when {

        vm.state.value.loading -> {
            CircularProgressIndicator()
        }

        vm.state.value.error != null -> {
            Text("Error: ${vm.state.value.error}")
        }

        else -> {

            val post = vm.state.value.data

            if (post != null) {
                PostItem(
                    post = post,
                    onLike = { vm.likePost(post.id) },
                    onShare = { vm.sharePost(post.id) },
                    onDetails = {}
                )
            } else {
                Text("No post found.")
            }

        }
    }
}