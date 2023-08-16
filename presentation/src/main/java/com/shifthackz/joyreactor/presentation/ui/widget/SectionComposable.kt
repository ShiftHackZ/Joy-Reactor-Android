package com.shifthackz.joyreactor.presentation.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shifthackz.joyreactor.entity.Section
import com.shifthackz.joyreactor.presentation.ui.screen.posts.PostsActionsListener

@Composable
fun SectionComposable(
    modifier: Modifier = Modifier,
    section: Section,
    listener: PostsActionsListener = PostsActionsListener.empty,
) {
    val shape = RoundedCornerShape(6.dp)
    Box(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize()
            .height(110.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer, shape)
            .clip(shape)
            .clickable { listener.openPosts(section.url) }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape),
            model = section.imageUrl,
            contentDescription = "bg",
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .background(Color(0x55000000))
                .defaultMinSize(minHeight = 110.dp)
                .fillMaxWidth(0.31f)
                .align(Alignment.CenterEnd),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = section.name,
                fontWeight = FontWeight.W700,
                fontSize = 20.sp,
            )
        }
    }
}
