package com.shifthackz.joyreactor.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shifthackz.joyreactor.entity.JoyReactorLink
import com.shifthackz.joyreactor.entity.Post
import com.shifthackz.joyreactor.presentation.R
import com.shifthackz.joyreactor.presentation.extensions.save
import com.shifthackz.joyreactor.presentation.extensions.shareFile
import com.shifthackz.joyreactor.presentation.navigation.Argument
import com.shifthackz.joyreactor.presentation.navigation.Route
import com.shifthackz.joyreactor.presentation.navigation.decodeNavArg
import com.shifthackz.joyreactor.presentation.navigation.encodeNavArg
import com.shifthackz.joyreactor.presentation.ui.screen.comments.CommentsScreen
import com.shifthackz.joyreactor.presentation.ui.screen.home.homeNavGraph
import com.shifthackz.joyreactor.presentation.ui.screen.posts.PostsActionsListener
import com.shifthackz.joyreactor.presentation.ui.screen.posts.PostsScreen
import com.shifthackz.joyreactor.presentation.ui.screen.settings.SettingsScreen
import com.shifthackz.joyreactor.presentation.ui.screen.slider.ContentSliderScreen
import com.shifthackz.joyreactor.presentation.ui.screen.tags.TagsScreen
import com.shifthackz.joyreactor.presentation.ui.screen.webview.WebViewScreen
import com.shifthackz.joyreactor.presentation.ui.theme.JoyYouTheme
import com.shifthackz.joyreactor.presentation.ui.theme.SetStatusBarColor
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

class JoyReactorActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContent {
            val navController = rememberNavController()
            val navigateBack: () -> Unit = { navController.navigateUp() }
            JoyYouTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val sliderScreenState = remember { mutableStateOf<Post?>(null) }
                    val webViewSheetState = remember { mutableStateOf<String?>(null) }
                    val postsActionsListener = object : PostsActionsListener {
                        override val openSettings: () -> Unit = {
                            navController.navigate(Route.SETTINGS.value)
                        }
                        override val openPosts: (String) -> Unit = { url ->
                            val route = if (url.endsWith("/rating")) Route.TAGS else Route.POSTS
                            navController.navigate(
                                Route.build(route, mapOf(Argument.URL to url.encodeNavArg()))
                            )
                        }
                        override val openSlider: (Post) -> Unit = { post ->
                            sliderScreenState.value = post
                        }
                        override val openComments: (String) -> Unit = { postId ->
                            navController.navigate(
                                Route.build(Route.COMMENTS, mapOf(Argument.POST_ID to postId))
                            )
                        }
                        override val openWebView: (String) -> Unit = { url ->
                            webViewSheetState.value = url
                        }
                        override val sharePostLink: (String) -> Unit = { url ->
                            startActivity(
                                Intent.createChooser(
                                    Intent(Intent.ACTION_SEND).apply {
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_TEXT, url)
                                    },
                                    null,
                                )
                            )
                        }
                    }
                    Box(Modifier.fillMaxSize()) {
                        NavHost(
                            navController = navController,
                            startDestination = Route.HOME.value,
                        ) {
                            homeNavGraph(
                                route = Route.HOME,
                                postsActionsListener = postsActionsListener,
                            )

                            composable(
                                route = Route.POSTS.value,
                                arguments = listOf(
                                    navArgument(Argument.URL) { type = NavType.StringType },
                                ),
                            ) { entry ->
                                val url = entry.arguments
                                    ?.getString(Argument.URL)
                                    ?.let(String::decodeNavArg)
                                    ?: JoyReactorLink.HOME_NEW.url

                                PostsScreen(
                                    viewModel = koinViewModel(
                                        parameters = { parametersOf(url.decodeNavArg()) },
                                    ),
                                    navigateBack = navigateBack,
                                    postsActionsListener = postsActionsListener,
                                ).Build()
                            }

                            composable(
                                route = Route.COMMENTS.value,
                                arguments = listOf(
                                    navArgument(Argument.POST_ID) { type = NavType.StringType },
                                ),
                            ) { entry ->
                                val postId = entry.arguments?.getString(Argument.POST_ID) ?: ""
                                CommentsScreen(
                                    viewModel = koinViewModel(
                                        parameters = { parametersOf(postId) }
                                    ),
                                    navigateBack = navigateBack,
                                ).Build()
                            }

                            composable(
                                route = Route.TAGS.value,
                                arguments = listOf(
                                    navArgument(Argument.URL) { type = NavType.StringType },
                                ),
                            ) { entry ->
                                val url = entry.arguments
                                    ?.getString(Argument.URL)
                                    ?.let(String::decodeNavArg)
                                    ?: JoyReactorLink.HOME_NEW.url

                                TagsScreen(
                                    viewModel = koinViewModel(
                                        parameters = { parametersOf(url) }
                                    ),
                                    navigateBack = navigateBack,
                                    postsActionsListener = postsActionsListener,
                                ).Build()
                            }

                            composable(Route.SETTINGS.value) {
                                SettingsScreen(
                                    viewModel = koinViewModel(),
                                    navigateBack = navigateBack,
                                ).Build()
                            }
                        }
                        AnimatedVisibility(
                            visible = webViewSheetState.value != null,
                            enter = slideInVertically(
                                initialOffsetY = {
                                    it / 2
                                },
                            ),
                            exit = slideOutVertically(
                                targetOffsetY = {
                                    it / 2
                                },
                            ),
                        ) {
                            webViewSheetState.value?.let { url ->
                                WebViewScreen(
                                    url = url,
                                    navigateBack = {
                                        webViewSheetState.value = null
                                    }
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = sliderScreenState.value != null,
                            enter = fadeIn(),
                            exit = fadeOut(),
                        ) {
                            sliderScreenState.value?.let { post ->
                                ContentSliderScreen(
                                    post = post,
                                    navigateBack = {
                                        sliderScreenState.value = null
                                    },
                                    shareImage = { bmp ->
                                        shareFile(
                                            bmp.save(this@JoyReactorActivity),
                                            "${this@JoyReactorActivity.packageName}.fileprovider",
                                            "image/jpeg",
                                            this@JoyReactorActivity.getString(R.string.share_title),
                                        )
                                    }
                                )
                            }
                        }
                        sliderScreenState.value ?: run { SetStatusBarColor() }
                    }
                }
            }
        }
    }
}
