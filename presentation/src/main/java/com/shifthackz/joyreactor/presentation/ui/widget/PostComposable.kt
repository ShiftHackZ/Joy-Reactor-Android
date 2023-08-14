@file:OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)

package com.shifthackz.joyreactor.presentation.ui.widget

import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmapOrNull
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.shifthackz.joyreactor.domain.entity.Content
import com.shifthackz.joyreactor.domain.entity.Post

@Composable
fun PostComposable(
    modifier: Modifier = Modifier,
    post: Post,
    openPosts: (String) -> Unit = {},
) {
    Column(
        modifier.padding(top = 12.dp),
    ) {
//        Text(
//            text = "==> BEGIN [${post.id}]"
//        )
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
                fontSize = 20.sp,
            )
        }
        FlowRow(
            modifier = Modifier
                .padding(all = 8.dp)
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            post.tags.forEach { tag ->
                Text(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .background(
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            shape = RoundedCornerShape(4.dp),
                        )
                        .clickable { openPosts(tag.url) }
                        .padding(all = 2.dp),
                    text = tag.name,
                )
            }
        }
        post.contents.groupBy(Content::type).toSortedMap().forEach {
            when (it.key) {
                Content.Type.IMAGE -> Column {
                    val pagerState = rememberPagerState()
                    val minHeightState = remember { mutableStateOf(0.001.dp) }

                    HorizontalPager(
                        modifier = Modifier.defaultMinSize(minHeight = minHeightState.value),
                        pageCount = it.value.size,
                        state = pagerState,
                    ) { index ->
                        RenderContent(
                            modifier = Modifier.defaultMinSize(minHeight = minHeightState.value),
                            content = it.value[index],
                            onHeight = { dp ->
                                if (minHeightState.value == 0.001.dp || dp > minHeightState.value) {
                                    minHeightState.value = dp
                                }
                            }
                        )
                    }
                    if (it.value.size > 1) Row(
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(16.dp)
                            .padding(start = 4.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        repeat(it.value.size) { iteration ->
                            val color = MaterialTheme.colorScheme.primary.copy(
                                alpha = if (pagerState.currentPage == iteration) 1.0f else 0.5f
                            )
                            Box(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .size(8.dp)
                            )
                        }
                    }
                }
                else ->  it.value.forEach { content ->
                    RenderContent(content = content)
                }
            }
        }
        Text(text = post.rating.toString())
//        Text(
//            text = "<== END [${post.id}]"
//        )
    }
}

@Composable
fun RenderContent(
    modifier: Modifier = Modifier,
    content: Content,
    onHeight: (Dp) -> Unit = {},
) {
    val textModifier = modifier
        .padding(horizontal = 8.dp)
        .padding(bottom = 12.dp)
    when (content) {
        is Content.Image -> {
            val localDensity = LocalDensity.current
            Box(modifier) {
                val drawableState = remember { mutableStateOf<Drawable?>(null) }
                drawableState.value?.toBitmapOrNull()?.let {
                    Image(
                        modifier = modifier.fillMaxSize().blur(64.dp),
                        painter = BitmapPainter(it.asImageBitmap()),
                        contentDescription = "bg",
                        contentScale = ContentScale.FillBounds,
                    )
                }

                AsyncImage(
                    modifier = modifier.fillMaxWidth(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .decoderFactory(
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                (ImageDecoderDecoder.Factory())
                            } else {
                                (GifDecoder.Factory())
                            }
                        )
                        .data(content.url)
                        .build(),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null,
                    onState = { state ->
                        when (state) {
                            is AsyncImagePainter.State.Success -> {
                                val drawable = state.result.drawable
                                drawableState.value = drawable
                                with(localDensity) {
                                    drawable
                                        .intrinsicHeight
                                        .toDp()
                                        .let(onHeight)
                                }
                            }

                            else -> Unit
                        }
                    }
                )
            }
        }
        is Content.Header -> Text(
            modifier = textModifier,
            text = content.text,
            fontSize = 18.sp,
        )
        is Content.Text -> Text(
            modifier = textModifier,
            text = content.text,
        )
    }
}
