package com.shifthackz.joyreactor.presentation.ui.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shifthackz.joyreactor.presentation.navigation.NavItem
import com.shifthackz.joyreactor.presentation.navigation.Route

@Composable
fun NestedNavComposable(
    modifier: Modifier = Modifier,
    navItems: List<NavItem>,
    topBar: @Composable (State<NavBackStackEntry?>, (Route) -> Unit) -> Unit = { _, _ -> },
    bottomBar: @Composable (State<NavBackStackEntry?>, (Route) -> Unit) -> Unit = { _, _ -> },
) {
    require(navItems.isNotEmpty()) {
        "Parameter navItems can not be empty collection!"
    }
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val navigate: (Route) -> Unit = { navRoute ->
        navController.navigate(navRoute.value) {
            navController.graph.startDestinationRoute?.let { route ->
                popUpTo(route) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val barVisiblePredicate = navItems.size > 1
    Scaffold(
        modifier = modifier,
        bottomBar = { if (barVisiblePredicate) bottomBar(backStackEntry, navigate) },
        topBar = { if (barVisiblePredicate) topBar(backStackEntry, navigate) }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = navItems.first().route.value,
        ) {
            navItems.forEach { item ->
                composable(item.route.value) {
                    item.content()
                }
            }
        }
    }
}
