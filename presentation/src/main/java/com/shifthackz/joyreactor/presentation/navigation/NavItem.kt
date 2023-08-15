package com.shifthackz.joyreactor.presentation.navigation

import androidx.compose.runtime.Composable
import com.shifthackz.joyreactor.presentation.entity.TextUI

abstract class NavItem {
    abstract val route: Route
    abstract val name: TextUI
    abstract val content: @Composable () -> Unit
}
