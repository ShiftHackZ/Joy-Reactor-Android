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
import com.shifthackz.joyreactor.entity.JoyReactorLink
import com.shifthackz.joyreactor.presentation.navigation.Argument
import com.shifthackz.joyreactor.presentation.navigation.Route
import com.shifthackz.joyreactor.presentation.navigation.decodeNavArg
import com.shifthackz.joyreactor.presentation.navigation.encodeNavArg
import com.shifthackz.joyreactor.presentation.ui.screen.home.homeNavGraph
import com.shifthackz.joyreactor.presentation.ui.screen.posts.PostsScreen
import com.shifthackz.joyreactor.presentation.ui.theme.JoyYouTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navigateBack: () -> Unit = { navController.navigateUp() }
            val openPosts: (String) -> Unit = { url ->
                navController.navigate(
                    Route.build(Route.POSTS, mapOf(Argument.URL to url.encodeNavArg()))
                )
            }
            JoyYouTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.HOME.value,
                    ) {
                        homeNavGraph(
                            route = Route.HOME,
                            openPosts = openPosts,
                        )

                        composable(
                            route = Route.POSTS.value,
                            arguments = listOf(
                                navArgument(Argument.URL) { type = NavType.StringType }
                            ),
                        ) { entry ->
                            val url = entry.arguments
                                ?.getString(Argument.URL)
                                ?.let(String::decodeNavArg)
                                ?: JoyReactorLink.NEW.url

                            PostsScreen(
                                viewModel = koinViewModel(
                                    parameters = { parametersOf(url) },
                                ),
                                navigateBack = navigateBack,
                                openPosts = openPosts,
                            ).Build()
                        }
                    }
                }
            }
        }
    }
}
