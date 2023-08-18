package com.shifthackz.joyreactor.presentation.ui.screen.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shifthackz.joyreactor.presentation.navigation.Route
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
        navItems = navItems.filterNot { it.route == Route.UNKNOWN },
        topBar = { backStackEntry, navigate ->
            val currentRoute = backStackEntry.value?.destination?.route
            Column {
                Box(modifier = Modifier.padding(all = 8.dp)) {
                    JoyReactorComposable()
                }
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
//                JoyReactorComposable()
//                Spacer(modifier = Modifier.weight(1f))
                    for (item in navItems) {
                        if (item.route == Route.UNKNOWN) {
                            Spacer(modifier = Modifier.weight(1f))
                            continue
                        }
                        val selected = item.route.value == currentRoute
                        ChipComposable(
                            modifier = Modifier.clickable { navigate(item.route) },
                            selected = selected,
                            text = item.name,
                            icon = item.icon,
                        )
                    }
                }
            }
        }
    )
}
