package com.shifthackz.joyreactor.presentation.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import com.shifthackz.joyreactor.presentation.entity.asString
import com.shifthackz.joyreactor.presentation.ui.theme.LightGray
import com.shifthackz.joyreactor.presentation.ui.theme.Orange
import com.shifthackz.joyreactor.presentation.ui.theme.OrangeDark
import com.shifthackz.joyreactor.presentation.ui.theme.OrangeSecondary
import com.shifthackz.joyreactor.presentation.ui.theme.OrangeSecondary50
import com.shifthackz.joyreactor.presentation.ui.theme.TextPrimary
import com.shifthackz.joyreactor.presentation.ui.theme.TextSecondary
import com.shifthackz.joyreactor.presentation.ui.theme.colors
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
                        label = {
                            Text(
                                text = item.name.asString(),
                                color = if (selected) colors(
                                    light = TextPrimary,
                                    dark = OrangeSecondary,
                                )
                                else colors(
                                    light = TextSecondary,
                                    dark = LightGray,
                                ),
                            )
                        },
                        onClick = { navigate(item.route) },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = colors(
                                light = Orange,
                                dark = OrangeSecondary50,
                            ),
                        ),
                        icon = {
                            Image(
                                imageVector = item.icon,
                                contentDescription = item.name.asString(),
                                colorFilter = ColorFilter.tint(
                                    color = if (selected) colors(
                                        light = TextPrimary,
                                        dark = OrangeDark,
                                    )
                                    else colors(
                                        light = TextSecondary,
                                        dark = LightGray,
                                    ),
                                    blendMode = BlendMode.SrcIn,
                                ),
                            )
                        },
                    )
                }
            }
        }
    )
}
