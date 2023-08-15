package com.shifthackz.joyreactor.presentation.ui.screen.feed

import androidx.compose.runtime.Composable
import com.shifthackz.joyreactor.entity.JoyReactorLink
import com.shifthackz.joyreactor.presentation.R
import com.shifthackz.joyreactor.presentation.entity.asTextUi
import com.shifthackz.joyreactor.presentation.navigation.Route
import com.shifthackz.joyreactor.presentation.ui.screen.posts.PostsScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun newTab(
    openPosts: (String) -> Unit,
) = FeedNavItem(
    route = Route.HOME_FEED_NEW,
    name = R.string.feed_tab_new.asTextUi(),
    content = {
        PostsScreen(
            viewModel = koinViewModel(
                parameters = { parametersOf(JoyReactorLink.NEW.url) }
            ),
            openPosts = openPosts,
        ).Build()
    }
)

@Composable
fun goodTab(
    openPosts: (String) -> Unit,
) = FeedNavItem(
    route = Route.HOME_FEED_GOOD,
    name = R.string.feed_tab_good.asTextUi(),
    content = {
        PostsScreen(
            viewModel = koinViewModel(
                parameters = { parametersOf(JoyReactorLink.GOOD.url) }
            ),
            openPosts = openPosts,
        ).Build()
    }
)

@Composable
fun bestTab(
    openPosts: (String) -> Unit,
) = FeedNavItem(
    route = Route.HOME_FEED_BEST,
    name = R.string.feed_tab_best.asTextUi(),
    content = {
        PostsScreen(
            viewModel = koinViewModel(
                parameters = { parametersOf(JoyReactorLink.BEST.url) }
            ),
            openPosts = openPosts,
        ).Build()
    }
)
