package com.shifthackz.joyreactor.presentation.ui.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.get
import com.shifthackz.joyreactor.presentation.R
import com.shifthackz.joyreactor.presentation.entity.asTextUi
import com.shifthackz.joyreactor.presentation.navigation.Route
import com.shifthackz.joyreactor.presentation.ui.screen.feed.FeedNavScreen
import com.shifthackz.joyreactor.presentation.ui.screen.feed.bestTab
import com.shifthackz.joyreactor.presentation.ui.screen.feed.goodTab
import com.shifthackz.joyreactor.presentation.ui.screen.feed.newTab

fun NavGraphBuilder.homeNavGraph(
    route: Route = Route.HOME,
    openPosts: (String) -> Unit,
) {
    addDestination(
        ComposeNavigator.Destination(provider[ComposeNavigator::class]) {
            HomeNavScreen(
                navItems = listOf(
                    feedTab(openPosts),
                ),
            )
        }.apply { this.route = route.value }
    )
}

@Composable
private fun feedTab(
    openPosts: (String) -> Unit,
) = HomeNavItem(
    route = Route.HOME_FEED,
    name = R.string.home_tab_feed.asTextUi(),
    icon = Icons.Default.Dashboard,
    content = {
        FeedNavScreen(
            navItems = listOf(
                newTab(openPosts),
                goodTab(openPosts),
                bestTab(openPosts),
            ),
        )
    }
)
