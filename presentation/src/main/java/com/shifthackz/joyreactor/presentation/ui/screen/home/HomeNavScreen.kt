package com.shifthackz.joyreactor.presentation.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shifthackz.joyreactor.presentation.entity.asString
import com.shifthackz.joyreactor.presentation.ui.widget.NestedNavComposable

@Composable
fun HomeNavScreen(
    modifier: Modifier = Modifier,
    navItems: List<HomeNavItem>,
) {
    NestedNavComposable(
        modifier = modifier,
        navItems = navItems,
        bottomBar = { backStackEntry, navigate ->
            NavigationBar {
                val currentRoute = backStackEntry.value?.destination?.route
                navItems.forEach { item ->
                    val selected = item.route.value == currentRoute
                    NavigationBarItem(
                        selected = selected,
                        label = { Text(text = item.name.asString()) },
                        onClick = { navigate(item.route) },
                        icon = {
                            Image(
                                imageVector = item.icon,
                                contentDescription = item.name.asString(),
                            )
                        },
                    )
                }
            }
        }
    )
}
