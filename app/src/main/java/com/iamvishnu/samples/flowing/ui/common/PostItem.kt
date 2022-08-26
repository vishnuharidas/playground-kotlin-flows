package com.iamvishnu.samples.flowing.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iamvishnu.samples.flowing.data.model.DummyPost

@Composable
fun PostItem(
    post: DummyPost,
    onLike: (String) -> Unit,
    onShare: (String) -> Unit,
    onDetails: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onDetails(post.id)
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = post.contents
        )

        Spacer(modifier = Modifier.weight(1.0f))

        OutlinedButton(onClick = { onLike(post.id) }) {
            Text("${post.likes} \uD83D\uDC4D")
        }

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedButton(onClick = { onShare(post.id) }) {
            Text("${post.shares} \uD83D\uDC8C")
        }

    }
}