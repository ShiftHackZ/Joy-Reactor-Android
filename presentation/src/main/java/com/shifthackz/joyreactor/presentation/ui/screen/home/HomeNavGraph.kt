package com.shifthackz.joyreactor.presentation.ui.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Segment
import androidx.compose.material.icons.filled.Topic
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.get
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
import com.shifthackz.joyreactor.presentation.ui.screen.posts.PostsActionsListener
import com.shifthackz.joyreactor.presentation.ui.screen.section.SectionScreen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.homeNavGraph(
    route: Route = Route.HOME,
    postsActionsListener: PostsActionsListener,
) {
    addDestination(
        ComposeNavigator.Destination(provider[ComposeNavigator::class]) {
            HomeNavScreen(
                navItems = listOf(
                    feedTab(postsActionsListener),
                    discussedTab(postsActionsListener),
                    sectionTab(postsActionsListener),
                ),
            )
        }.apply { this.route = route.value }
    )
}

@Composable
private fun feedTab(postsActionsListener: PostsActionsListener) = HomeNavItem(
    route = Route.HOME_FEED,
    name = R.string.home_tab_feed.asTextUi(),
    icon = Icons.Default.Dashboard,
    content = {
        FeedNavScreen(
            navItems = listOf(
//                testTab(openPosts, openSlider, openComments),
                feedNewTab(postsActionsListener),
                feedGoodTab(postsActionsListener),
                feedBestTab(postsActionsListener),
            ),
        )
    }
)

@Composable
private fun discussedTab(postsActionsListener: PostsActionsListener) = HomeNavItem(
    route = Route.HOME_DISCUSSED,
    name = R.string.home_tab_discussed.asTextUi(),
    icon = Icons.Default.Topic,
    content = {
        FeedNavScreen(
            navItems = listOf(
                discussedAllTab(postsActionsListener),
                discussedGoodTab(postsActionsListener),
                discussedFlameTab(postsActionsListener),
            )
        )
    }
)

@Composable
private fun sectionTab(postsActionsListener: PostsActionsListener) = HomeNavItem(
    route = Route.HOME_SECTION,
    name = R.string.home_tab_sections.asTextUi(),
    icon = Icons.Default.Segment,
    content = {
        SectionScreen(
            viewModel = koinViewModel(),
            listener = postsActionsListener,
        ).Build()
    }
)
