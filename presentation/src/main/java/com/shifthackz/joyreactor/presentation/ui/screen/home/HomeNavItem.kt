package com.shifthackz.joyreactor.presentation.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.shifthackz.joyreactor.presentation.entity.TextUI
import com.shifthackz.joyreactor.presentation.navigation.NavItem
import com.shifthackz.joyreactor.presentation.navigation.Route

data class HomeNavItem(
    override val route: Route,
    override val name: TextUI,
    override val content: @Composable () -> Unit,
    val icon: ImageVector,
) : NavItem()
