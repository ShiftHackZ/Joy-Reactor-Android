package com.shifthackz.joyreactor.presentation.ui.screen.feed

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.shifthackz.joyreactor.presentation.entity.TextUI
import com.shifthackz.joyreactor.presentation.navigation.NavItem
import com.shifthackz.joyreactor.presentation.navigation.Route

data class FeedNavItem(
    override val route: Route,
    override val name: TextUI = TextUI.empty,
    val icon: ImageVector? = null,
    override val content: @Composable () -> Unit = {},
) : NavItem()
