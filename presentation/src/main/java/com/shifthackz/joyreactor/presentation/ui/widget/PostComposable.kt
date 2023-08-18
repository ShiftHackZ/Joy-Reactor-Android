@file:OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)

package com.shifthackz.joyreactor.presentation.ui.widget

import android.graphics.drawable.Drawable
import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.drawable.toBitmapOrNull
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.shifthackz.joyreactor.entity.Author
import com.shifthackz.joyreactor.entity.Content
import com.shifthackz.joyreactor.entity.JoyReactorLink
import com.shifthackz.joyreactor.entity.Post
import com.shifthackz.joyreactor.entity.Tag
import com.shifthackz.joyreactor.presentation.entity.TextUI
import com.shifthackz.joyreactor.presentation.entity.asString
import com.shifthackz.joyreactor.presentation.navigation.decodeNavArg
import com.shifthackz.joyreactor.presentation.ui.formatter.PostDateFormatter
import com.shifthackz.joyreactor.presentation.ui.formatter.PostTimeFormatter
import com.shifthackz.joyreactor.presentation.ui.screen.posts.PostsActionsListener
import com.shifthackz.joyreactor.presentation.ui.theme.LightGray
import com.shifthackz.joyreactor.presentation.ui.theme.LightGray20
import java.util.Date


@Composable
fun PostComposable(
    modifier: Modifier = Modifier,
    post: Post,
    postsActionsListener: PostsActionsListener = PostsActionsListener.empty,
) {
    Column(
        modifier = modifier.padding(top = 12.dp, bottom = 22.dp),
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
                        .clickable { postsActionsListener.openPosts(tag.url) }
                        .padding(all = 2.dp),
                    text = tag.name,
                )
            }
        }
        post.contents.groupBy(Content::type).toSortedMap().forEach {
            when (it.key) {
                Content.Type.MEDIA -> Column {
                    val pagerState = rememberPagerState { it.value.size }
                    val minHeightState = remember { mutableStateOf(300.dp) }
                    HorizontalPager(
                        modifier = Modifier
                            .clickable { postsActionsListener.openSlider(post) }
                            .defaultMinSize(minHeight = minHeightState.value),
                        state = pagerState,
                    ) { index ->
                        RenderPostContent(
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
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        repeat(it.value.size) { iteration ->
                            val isActive = pagerState.currentPage == iteration
                            val color = MaterialTheme.colorScheme.primary.copy(
                                alpha = if (isActive) 1.0f else 0.5f
                            )
                            Box(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .size((if (isActive) 8 else 5).dp)
                            )
                        }
                    }
                }

                else -> it.value.forEach { content ->
                    RenderPostContent(content = content)
                }
            }
        }
        Row(
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(horizontal = 6.dp),
                text = PostDateFormatter()(post.date).asString(),
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = Icons.Default.AccessTime,
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(horizontal = 6.dp),
                text = PostTimeFormatter()(post.date).asString(),
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PostActonButton(
                icon = Icons.Default.Star,
                text = TextUI.Static("${post.rating}"),
            )
            Spacer(modifier = Modifier.weight(1f))
            PostActonButton(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { postsActionsListener.openWebView(post.url) },
                icon = Icons.Default.Language,
            )
            PostActonButton(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { postsActionsListener.sharePostLink(post.url) },
                icon = Icons.Default.Share,
            )
            PostActonButton(
                modifier = Modifier.clickable { postsActionsListener.openComments(post.id) },
                icon = Icons.Filled.Comment,
                text = TextUI.Static("${post.estimatedCommentsCount}"),
            )
        }
    }
}

@Composable
fun RenderPostContent(
    modifier: Modifier = Modifier,
    content: Content,
    imageBlur: Boolean = true,
    imageZoom: Boolean = false,
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
                drawableState.value?.takeIf { imageBlur }?.toBitmapOrNull()?.let { bmp ->
                    Image(
                        modifier = modifier
                            .fillMaxSize()
                            .blur(64.dp),
                        painter = BitmapPainter(bmp.asImageBitmap()),
                        contentDescription = "bg",
                        contentScale = ContentScale.FillBounds,
                    )
                }

                val request = ImageRequest.Builder(LocalContext.current)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .decoderFactory(
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            (ImageDecoderDecoder.Factory())
                        } else {
                            (GifDecoder.Factory())
                        }
                    )
                    .data(content.url)
                    .build()

                val ctx = LocalContext.current
                val lambda: () -> Unit = {
                    ImageLoader.Builder(ctx)
                        .memoryCache(
                            MemoryCache.Builder(ctx)
                                .maxSizePercent(0.0)
                                .build()
                        )
                        .build()
                        .enqueue(request)
                }
                LaunchedEffect(Unit) { lambda()  }
                val isLoading = remember { mutableStateOf(true) }
                val isError = remember { mutableStateOf(false) }

                Box {
                    AsyncImage(
                        modifier = modifier
                            .alpha(if (isLoading.value || isError.value) 0f else 1f)
                            .fillMaxWidth(),
                        model = request,
                        contentScale = ContentScale.FillWidth,
                        contentDescription = null,
                        onState = { state ->
                            when (state) {
                                is AsyncImagePainter.State.Loading -> {
                                    isLoading.value = true
                                    isError.value = false
                                }

                                is AsyncImagePainter.State.Success -> {
                                    isLoading.value = false
                                    isError.value = false
                                    val drawable = state.result.drawable
                                    drawableState.value = drawable
                                    with(localDensity) {
                                        drawable
                                            .intrinsicHeight
                                            .toDp()
                                            .let(onHeight)
                                    }
                                }

                                is AsyncImagePainter.State.Error -> {
                                    isLoading.value = false
                                    isError.value = true
                                }

                                else -> Unit
                            }
                        }
                    )
                    if (isLoading.value) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .shimmer()
                        )
                    }
                    if (isError.value) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Text("Error loading content")
                            Button(onClick = {
                                lambda()
                            }) {
                                Text("Retry")
                            }
                        }
                    }
                }
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

        is Content.Video -> {
            val defaultDataSourceFactory = DefaultHttpDataSource.Factory()
                .setConnectTimeoutMs(DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS)
                .setReadTimeoutMs(DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS)
                .setDefaultRequestProperties(
                    mapOf("Referer" to JoyReactorLink.HOME_BEST.url)
                )
                .setAllowCrossProtocolRedirects(false)
                .setKeepPostFor302Redirects(false)

            val dataSourceFactory = DefaultDataSource.Factory(
                LocalContext.current,
                defaultDataSourceFactory,
            )

            val exoPlayer = ExoPlayer.Builder(LocalContext.current)
                .setMediaSourceFactory(DefaultMediaSourceFactory(dataSourceFactory))
                .build()
                .apply {
                    val url = content.url.decodeNavArg()
                    setMediaItem(MediaItem.fromUri(url))
                }

            AndroidView(
                modifier = modifier.fillMaxSize(),
                factory = { ctx ->
                    StyledPlayerView(ctx).apply {
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
                        controllerHideOnTouch = true
                        controllerShowTimeoutMs = 1500
                        player = exoPlayer.apply { prepare() }
                    }
                }
            )

            DisposableEffect(
                key1 = Unit,
                effect = {
                    onDispose { exoPlayer.release() }
                },
            )
        }
    }
}

@Composable
fun PostActonButton(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    text: TextUI = TextUI.empty,
    icon: ImageVector,
) {
    Row(
        modifier = modifier
            .background(
                color = if (isSystemInDarkTheme()) LightGray20 else LightGray,
                shape = RoundedCornerShape(8.dp),
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            modifier = iconModifier
                .padding(vertical = 8.dp)
                .padding(start = 8.dp, end = 5.dp)
                .size(18.dp),
            imageVector = icon,
            contentDescription = null,
        )
        val label = text.asString()
        if (label.isNotBlank()) {
            Text(
                modifier = textModifier.padding(end = 8.dp, bottom = 2.dp),
                text = label,
                fontSize = 20.sp,
            )
        } else {
            Box(modifier = Modifier.padding(end = 3.dp))
        }
    }
}

@Composable
fun PostShimmer(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(top = 12.dp, bottom = 22.dp),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .shimmer(),
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(height = 20.dp, width = 100.dp)
                    .shimmer(),
            )
        }
        Row(
            modifier = Modifier
                .padding(all = 8.dp)
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            repeat((0..3).toList().size) {
                Box(
                    modifier = Modifier
                        .size(height = 18.dp, width = 60.dp)
                        .shimmer(shape = RoundedCornerShape(4.dp)),
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.4f)
                .shimmer(),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val actionModifier = Modifier
                .size(width = 70.dp, height = 34.dp)
                .shimmer(shape = RoundedCornerShape(6.dp))
            Box(modifier = actionModifier)
            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = actionModifier)
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewPostContent(@PreviewParameter(LoremIpsum::class) lorem: String) {
    PostComposable(
        post = Post(
            id = "",
            author = Author(
                name = "User Name",
                avatarUrl = "",
            ),
            contents = listOf(
                Content.Text(lorem.take(500))
            ),
            tags = listOf(
                Tag(
                    name = "tag", url = "", imageUrl = "",
                ),
            ),
            rating = 0.0,
            comments = listOf(),
            estimatedCommentsCount = 1,
            date = Date(),
            url = "",
        )
    )
}

@Composable
@Preview(showBackground = true)
private fun PreviewPostShimmer() {
    PostShimmer()
}