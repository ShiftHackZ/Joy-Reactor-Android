package com.shifthackz.joyreactor.presentation.ui.screen.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shifthackz.joyreactor.presentation.ui.widget.ChipComposable
import com.shifthackz.joyreactor.presentation.ui.widget.JoyReactorComposable
import com.shifthackz.joyreactor.presentation.ui.widget.NestedNavComposable

@Composable
fun FeedNavScreen(
    modifier: Modifier = Modifier,
    navItems: List<FeedNavItem>,
) {
    NestedNavComposable(
        modifier = modifier,
        navItems = navItems,
        topBar = { backStackEntry, navigate ->
            val currentRoute = backStackEntry.value?.destination?.route
            Row(
                modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                JoyReactorComposable()
                Spacer(modifier = Modifier.weight(1f))
                navItems.forEach { item ->
                    val selected = item.route.value == currentRoute
                    ChipComposable(
                        modifier = Modifier.clickable { navigate(item.route) },
                        selected = selected,
                        text = item.name,
                    )
                }
            }
        }
    )
}
