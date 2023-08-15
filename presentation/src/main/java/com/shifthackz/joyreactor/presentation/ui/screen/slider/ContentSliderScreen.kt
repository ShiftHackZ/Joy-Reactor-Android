@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package com.shifthackz.joyreactor.presentation.ui.screen.slider

import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
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

//class ContentSliderScreen(
//    private val viewModel: ContentSliderViewModel,
//    private val navigateBack: () -> Unit = {},
//) : MviStateScreen<ContentSliderState>(viewModel) {
//
//    @Composable
//    override fun Content() {
//        ScreenContent(
//            modifier = Modifier.fillMaxSize(),
//            state = viewModel.state.collectAsStateWithLifecycle().value,
//            navigateBack = navigateBack,
//        )
//    }
//}

@Composable
fun ContentSliderScreen(
    modifier: Modifier = Modifier,
    post: Post,
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


        Log.d("DBG000", "MEDIA : $media")
        HorizontalPager(
            modifier = contentModifier,
            state = pagerState,
        ) { index ->
            val zoomState = rememberZoomState()
            val request = ImageRequest.Builder(LocalContext.current)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .decoderFactory(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        (ImageDecoderDecoder.Factory())
                    } else {
                        (GifDecoder.Factory())
                    }
                )
                .data(media[index].value)
                .build()

            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .zoomable(zoomState),
                painter = rememberAsyncImagePainter(model = request),
                contentDescription = null,
            )
        }


    }
}
