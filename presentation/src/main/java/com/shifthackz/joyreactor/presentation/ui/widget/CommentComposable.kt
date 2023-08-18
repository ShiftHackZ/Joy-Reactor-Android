package com.shifthackz.joyreactor.presentation.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shifthackz.joyreactor.entity.Author
import com.shifthackz.joyreactor.entity.Comment
import com.shifthackz.joyreactor.presentation.ui.screen.comments.CommentsUiModel

private val imageSize = 28.dp
private val offset = imageSize + 6.dp

@Composable
fun CommentComposable(
    modifier: Modifier = Modifier,
    commentUi: CommentsUiModel,
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                modifier = Modifier.size(imageSize),
                model = commentUi.comment.author.avatarUrl,
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(start = offset - imageSize),
                text = commentUi.comment.author.name,
                fontWeight = FontWeight.W600,
                fontSize = 18.sp,
            )
        }
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = commentUi.comment.text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.W400,
        )
        commentUi.children.forEach {
            CommentComposable(
                modifier = Modifier.padding(start = imageSize / 2),
                commentUi = it,
            )
        }
    }
}

@Composable
fun CommentShimmer(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier.size(imageSize).shimmer(),
            )
            Box(
                modifier = Modifier
                    .size(width = 120.dp, height = 18.dp)
                    .padding(start = offset - imageSize)
                    .shimmer(),
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(14.dp)
                .shimmer(),
        )
        Box(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .height(14.dp)
                .shimmer(),
        )
        Box(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(0.6f)
                .height(14.dp)
                .shimmer(),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewCommentContent(@PreviewParameter(LoremIpsum::class) lorem: String) {
    val author = Author(
        "User Name",
        "https://img2.joyreactor.cc/images/default_avatar_50.gif",
    )
    CommentComposable(
        commentUi =
        CommentsUiModel(
            Comment(
                "1",
                null,
                lorem.take(100),
                author,
                rating = 0.0,
            ),
            listOf(
                CommentsUiModel(
                    Comment(
                        "1",
                        null,
                        lorem.take(200),
                        author,
                        rating = 0.0,
                    ),
                    listOf(),
                ),
            ),
        )
    )
}

@Composable
@Preview(showBackground = true)
private fun PreviewCommentShimmer() {
    CommentShimmer()
}
