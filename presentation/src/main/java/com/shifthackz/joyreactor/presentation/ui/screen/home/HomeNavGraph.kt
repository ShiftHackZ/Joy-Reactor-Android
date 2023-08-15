package com.shifthackz.joyreactor.presentation.ui.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Topic
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.get
import com.shifthackz.joyreactor.entity.Post
import com.shifthackz.joyreactor.presentation.R
import com.shifthackz.joyreactor.presentation.entity.asTextUi
import com.shifthackz.joyreactor.presentation.navigation.Route
import com.shifthackz.joyreactor.presentation.ui.screen.feed.FeedNavScreen
import com.shifthackz.joyreactor.presentation.ui.screen.feed.discussedAllTab
import com.shifthackz.joyreactor.presentation.ui.screen.feed.discussedFlameTab
import com.shifthackz.joyreactor.presentation.ui.screen.feed.discussedGoodTab
import com.shifthackz.joyreactor.presentation.ui.screen.feed.feedBestTab
import com.shifthackz.joyreactor.presentation.ui.screen.feed.feedGoodTab
import com.shifthackz.joyreactor.presentation.ui.screen.feed.feedNewTab

fun NavGraphBuilder.homeNavGraph(
    route: Route = Route.HOME,
    openPosts: (String) -> Unit,
    openSlider: (Post) -> Unit,
) {
    addDestination(
        ComposeNavigator.Destination(provider[ComposeNavigator::class]) {
            HomeNavScreen(
                navItems = listOf(
                    feedTab(openPosts = openPosts, openSlider = openSlider),
                    discussedTab(openPosts = openPosts, openSlider = openSlider),
                ),
            )
        }.apply { this.route = route.value }
    )
}

@Composable
private fun feedTab(
    openPosts: (String) -> Unit,
    openSlider: (Post) -> Unit,
) = HomeNavItem(
    route = Route.HOME_FEED,
    name = R.string.home_tab_feed.asTextUi(),
    icon = Icons.Default.Dashboard,
    content = {
        FeedNavScreen(
            navItems = listOf(
                feedNewTab(openPosts = openPosts, openSlider = openSlider),
                feedGoodTab(openPosts = openPosts, openSlider = openSlider),
                feedBestTab(openPosts = openPosts, openSlider = openSlider),
//                testTab(openPosts = openPosts, openSlider = openSlider),
            ),
        )
    }
)

@Composable
private fun discussedTab(
    openPosts: (String) -> Unit,
    openSlider: (Post) -> Unit,
) = HomeNavItem(
    route = Route.HOME_DISCUSSED,
    name = R.string.home_tab_discussed.asTextUi(),
    icon = Icons.Default.Topic,
    content = {
        FeedNavScreen(
            navItems = listOf(
                discussedAllTab(openPosts = openPosts, openSlider = openSlider),
                discussedGoodTab(openPosts = openPosts, openSlider = openSlider),
                discussedFlameTab(openPosts = openPosts, openSlider = openSlider),
            )
        )
    }
)
