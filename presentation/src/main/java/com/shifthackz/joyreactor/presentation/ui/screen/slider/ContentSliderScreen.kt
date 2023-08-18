@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package com.shifthackz.joyreactor.presentation.ui.screen.slider

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.shifthackz.joyreactor.entity.Content
import com.shifthackz.joyreactor.entity.Post
import com.shifthackz.joyreactor.presentation.entity.ToolbarUI
import com.shifthackz.joyreactor.presentation.ui.theme.SetStatusBarColor
import com.shifthackz.joyreactor.presentation.ui.widget.PagerIndicatorComposable
import com.shifthackz.joyreactor.presentation.ui.widget.ToolbarComposable
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Composable
fun ContentSliderScreen(
    modifier: Modifier = Modifier,
    post: Post,
    shareImage: (Bitmap) -> Unit = {},
    navigateBack: () -> Unit = {},
) {
    BackHandler {
        navigateBack()
    }
    val media = post
        .contents
        .filter { it.type == Content.Type.MEDIA && it.identifier == Content.Identifier.IMAGE }

    val bgColor = Color.Black
    val pagerState = rememberPagerState { media.size }
    val images = remember { arrayOfNulls<Bitmap?>(media.size) }
    val request: (Context, Int) -> ImageRequest = { ctx, index ->
        ImageRequest.Builder(ctx)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .decoderFactory(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    (ImageDecoderDecoder.Factory())
                } else {
                    (GifDecoder.Factory())
                }
            )
            .data(media[index].value)
            .listener(onSuccess = { req, res ->
                res.drawable.toBitmap().let {
                    images[index] = it
                }
            })
            .build()
    }
    SetStatusBarColor(bgColor)
    Scaffold(
        modifier = modifier,
        containerColor = bgColor,
        topBar = {
            ToolbarComposable(
                toolbarUI = ToolbarUI(
                    backButton = true,
                ),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = bgColor,
                    navigationIconContentColor = Color.White,
                ),
                navigateBack = navigateBack,
                actions = {
                    IconButton(
                        onClick = {
                            images[pagerState.currentPage]?.let(shareImage)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null,
                            tint = Color.White/*.copy(
                                alpha = images[pagerState.currentPage]?.let { 1.0f } ?: 0.5f,
                            )*/,
                        )
                    }
                }
            )
        },
        bottomBar = {
            Column(Modifier.fillMaxWidth()) {
                PagerIndicatorComposable(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    size = media.size,
                    state = pagerState,
                )
            }
        }
    ) { paddingValues ->
        val contentModifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()

        HorizontalPager(
            modifier = contentModifier,
            state = pagerState,
        ) { index ->
            val zoomState = rememberZoomState()
            val ctx = LocalContext.current
            val imgRequest = request(ctx, index)

            LaunchedEffect(Unit) {
                ImageLoader.Builder(ctx)
                    .memoryCache(
                        MemoryCache.Builder(ctx)
                            .maxSizePercent(0.0)
                            .build()
                    )
                    .build()
                    .enqueue(imgRequest)
            }
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .zoomable(zoomState),
                painter = rememberAsyncImagePainter(
                    model = imgRequest,
                ),
                contentDescription = null,
            )
        }
    }
}
