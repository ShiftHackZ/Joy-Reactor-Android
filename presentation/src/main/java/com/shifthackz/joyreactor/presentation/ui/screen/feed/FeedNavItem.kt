package com.shifthackz.joyreactor.presentation.ui.screen.feed

import androidx.compose.runtime.Composable
import com.shifthackz.joyreactor.presentation.entity.TextUI
import com.shifthackz.joyreactor.presentation.navigation.NavItem
import com.shifthackz.joyreactor.presentation.navigation.Route

data class FeedNavItem(
    override val route: Route,
    override val name: TextUI,
    override val content: @Composable () -> Unit,
) : NavItem()
