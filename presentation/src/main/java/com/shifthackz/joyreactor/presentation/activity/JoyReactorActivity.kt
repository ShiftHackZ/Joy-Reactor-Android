package com.shifthackz.joyreactor.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shifthackz.joyreactor.presentation.navigation.Route
import com.shifthackz.joyreactor.presentation.ui.screen.test.PostsScreen
import com.shifthackz.joyreactor.presentation.ui.screen.test.PostsViewModel
import com.shifthackz.joyreactor.presentation.ui.theme.JoyYouTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navigateBack: () -> Unit = { navController.navigateUp() }
            JoyYouTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.POSTS.value,
                    ) {
                        composable(
                            route = Route.POSTS.value,
                            arguments = listOf(
                                navArgument("url") { type = NavType.StringType }
                            ),
                        ) { entry ->
                            val url = entry.arguments
                                ?.getString("url")
                                ?.let {
                                    URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
                                }
                                ?: "https://joyreactor.cc/all"
//                                ?: "https://PR.reactor.cc/"
//                                ?: "https://joyreactor.cc/tag/%D0%B4%D0%B5%D0%BD%D1%8C+%D1%80%D0%BE%D0%B6%D0%B4%D0%B5%D0%BD%D0%B8%D1%8F+%D1%80%D0%B5%D0%B0%D0%BA%D1%82%D0%BE%D1%80%D1%87%D0%B0%D0%BD"

                            val viewModel = koinViewModel<PostsViewModel>(
                                parameters = {
                                    parametersOf(url)
                                }
                            )
                            PostsScreen(
                                viewModel = viewModel,
                                navigateBack = navigateBack,
                                openPosts = { rawUrl ->
                                    val encodedUrl = URLEncoder.encode(rawUrl, StandardCharsets.UTF_8.toString())
                                    navController.navigate(
                                        Route.build(Route.POSTS, mapOf("url" to encodedUrl))
                                    )
                                },
                            ).Build()
                        }
                    }
                }
            }
        }
    }
}
