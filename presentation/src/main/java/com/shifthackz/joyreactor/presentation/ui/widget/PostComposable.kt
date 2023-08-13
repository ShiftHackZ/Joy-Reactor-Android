@file:OptIn(ExperimentalLayoutApi::class)

package com.shifthackz.joyreactor.presentation.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.shifthackz.joyreactor.domain.entity.Content
import com.shifthackz.joyreactor.domain.entity.Post

@Composable
fun PostComposable(
    modifier: Modifier = Modifier,
    post: Post,
) {
    Column(
        modifier.padding(top = 12.dp),
    ) {
        Text(
            text = "==> BEGIN [${post.id}]"
        )
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier.size(42.dp),
                model = post.author.avatarUrl,
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = post.author.name,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        FlowRow(
            modifier = Modifier.padding(all = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            post.tags.forEach { tag ->
                Text(text = tag.name)
            }
        }
        post.contents.forEach { content ->
            when (content) {
                is Content.Image -> AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = content.url,
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null,
                )
            }
        }
        Text(text = post.rating.toString())
        Text(
            text = "<== END [${post.id}]"
        )
    }
}
