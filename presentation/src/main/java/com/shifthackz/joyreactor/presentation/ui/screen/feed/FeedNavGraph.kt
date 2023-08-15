package com.shifthackz.joyreactor.presentation.ui.screen.feed

import androidx.compose.runtime.Composable
import com.shifthackz.joyreactor.entity.JoyReactorLink
import com.shifthackz.joyreactor.entity.Post
import com.shifthackz.joyreactor.presentation.R
import com.shifthackz.joyreactor.presentation.entity.asTextUi
import com.shifthackz.joyreactor.presentation.navigation.Route
import com.shifthackz.joyreactor.presentation.ui.screen.posts.PostsScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun feedNewTab(
    openPosts: (String) -> Unit,
    openSlider: (Post) -> Unit,
) = FeedNavItem(
    route = Route.HOME_FEED_NEW,
    name = R.string.feed_tab_new.asTextUi(),
    content = {
        PostsScreen(
            viewModel = koinViewModel(
                parameters = { parametersOf(JoyReactorLink.HOME_NEW.url) }
            ),
            openPosts = openPosts,
            openSlider = openSlider,
        ).Build()
    }
)

@Composable
fun feedGoodTab(
    openPosts: (String) -> Unit,
    openSlider: (Post) -> Unit,
) = FeedNavItem(
    route = Route.HOME_FEED_GOOD,
    name = R.string.feed_tab_good.asTextUi(),
    content = {
        PostsScreen(
            viewModel = koinViewModel(
                parameters = { parametersOf(JoyReactorLink.HOME_GOOD.url) }
            ),
            openPosts = openPosts,
            openSlider = openSlider,
        ).Build()
    }
)

@Composable
fun feedBestTab(
    openPosts: (String) -> Unit,
    openSlider: (Post) -> Unit,
) = FeedNavItem(
    route = Route.HOME_FEED_BEST,
    name = R.string.feed_tab_best.asTextUi(),
    content = {
        PostsScreen(
            viewModel = koinViewModel(
                parameters = { parametersOf(JoyReactorLink.HOME_BEST.url) }
            ),
            openPosts = openPosts,
            openSlider = openSlider,
        ).Build()
    }
)

@Composable
fun discussedAllTab(
    openPosts: (String) -> Unit,
    openSlider: (Post) -> Unit,
) = FeedNavItem(
    route = Route.HOME_DISCUSSED_ALL,
    name = R.string.discussed_tab_all.asTextUi(),
    content = {
        PostsScreen(
            viewModel = koinViewModel(
                parameters = { parametersOf(JoyReactorLink.DISCUSSED_ALL.url) }
            ),
            openPosts = openPosts,
            openSlider = openSlider,
        ).Build()
    }
)

@Composable
fun discussedGoodTab(
    openPosts: (String) -> Unit,
    openSlider: (Post) -> Unit,
) = FeedNavItem(
    route = Route.HOME_DISCUSSED_GOOD,
    name = R.string.discussed_tab_good.asTextUi(),
    content = {
        PostsScreen(
            viewModel = koinViewModel(
                parameters = { parametersOf(JoyReactorLink.DISCUSSED_GOOD.url) }
            ),
            openPosts = openPosts,
            openSlider = openSlider,
        ).Build()
    }
)

@Composable
fun discussedFlameTab(
    openPosts: (String) -> Unit,
    openSlider: (Post) -> Unit,
) = FeedNavItem(
    route = Route.HOME_DISCUSSED_FLAME,
    name = R.string.discussed_tab_flame.asTextUi(),
    content = {
        PostsScreen(
            viewModel = koinViewModel(
                parameters = { parametersOf(JoyReactorLink.DISCUSSED_FLAME.url) }
            ),
            openPosts = openPosts,
            openSlider = openSlider,
        ).Build()
    }
)

@Composable
fun testTab(
    openPosts: (String) -> Unit,
    openSlider: (Post) -> Unit,
) = FeedNavItem(
    route = Route.HOME_FEED_TEST,
    name = "Test".asTextUi(),
    content = {
        PostsScreen(
            viewModel = koinViewModel(
                parameters = { parametersOf("https://joyreactor.cc/tag/art") }
            ),
            openPosts = openPosts,
            openSlider = openSlider,
        ).Build()
    }
)
