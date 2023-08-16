package com.shifthackz.joyreactor.presentation.ui.screen.feed

import androidx.compose.runtime.Composable
import com.shifthackz.joyreactor.entity.JoyReactorLink
import com.shifthackz.joyreactor.presentation.R
import com.shifthackz.joyreactor.presentation.entity.asTextUi
import com.shifthackz.joyreactor.presentation.navigation.Route
import com.shifthackz.joyreactor.presentation.ui.screen.posts.PostsActionsListener
import com.shifthackz.joyreactor.presentation.ui.screen.posts.PostsScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun feedNewTab(postsActionsListener: PostsActionsListener) = FeedNavItem(
    route = Route.HOME_FEED_NEW,
    name = R.string.feed_tab_new.asTextUi(),
    content = {
        PostsScreen(
            viewModel = koinViewModel(
                parameters = { parametersOf(JoyReactorLink.HOME_NEW.url) }
            ),
            postsActionsListener = postsActionsListener,
        ).Build()
    }
)

@Composable
fun feedGoodTab(postsActionsListener: PostsActionsListener) = FeedNavItem(
    route = Route.HOME_FEED_GOOD,
    name = R.string.feed_tab_good.asTextUi(),
    content = {
        PostsScreen(
            viewModel = koinViewModel(
                parameters = { parametersOf(JoyReactorLink.HOME_GOOD.url) }
            ),
            postsActionsListener = postsActionsListener,
        ).Build()
    }
)

@Composable
fun feedBestTab(postsActionsListener: PostsActionsListener) = FeedNavItem(
    route = Route.HOME_FEED_BEST,
    name = R.string.feed_tab_best.asTextUi(),
    content = {
        PostsScreen(
            viewModel = koinViewModel(
                parameters = { parametersOf(JoyReactorLink.HOME_BEST.url) }
            ),
            postsActionsListener = postsActionsListener,
        ).Build()
    }
)

@Composable
fun discussedAllTab(postsActionsListener: PostsActionsListener) = FeedNavItem(
    route = Route.HOME_DISCUSSED_ALL,
    name = R.string.discussed_tab_all.asTextUi(),
    content = {
        PostsScreen(
            viewModel = koinViewModel(
                parameters = { parametersOf(JoyReactorLink.DISCUSSED_ALL.url) }
            ),
            postsActionsListener = postsActionsListener,
        ).Build()
    }
)

@Composable
fun discussedGoodTab(postsActionsListener: PostsActionsListener) = FeedNavItem(
    route = Route.HOME_DISCUSSED_GOOD,
    name = R.string.discussed_tab_good.asTextUi(),
    content = {
        PostsScreen(
            viewModel = koinViewModel(
                parameters = { parametersOf(JoyReactorLink.DISCUSSED_GOOD.url) }
            ),
            postsActionsListener = postsActionsListener,
        ).Build()
    }
)

@Composable
fun discussedFlameTab(postsActionsListener: PostsActionsListener) = FeedNavItem(
    route = Route.HOME_DISCUSSED_FLAME,
    name = R.string.discussed_tab_flame.asTextUi(),
    content = {
        PostsScreen(
            viewModel = koinViewModel(
                parameters = { parametersOf(JoyReactorLink.DISCUSSED_FLAME.url) }
            ),
            postsActionsListener = postsActionsListener,
        ).Build()
    }
)

@Composable
fun testTab(postsActionsListener: PostsActionsListener) = FeedNavItem(
    route = Route.HOME_FEED_TEST,
    name = "Test".asTextUi(),
    content = {
        PostsScreen(
            viewModel = koinViewModel(
                parameters = { parametersOf("https://joyreactor.cc/tag/Anime+Ero") }
            ),
            postsActionsListener = postsActionsListener,
        ).Build()
    }
)
