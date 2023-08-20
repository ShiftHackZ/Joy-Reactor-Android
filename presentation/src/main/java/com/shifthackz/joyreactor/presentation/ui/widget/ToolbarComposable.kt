@file:OptIn(ExperimentalMaterial3Api::class)

package com.shifthackz.joyreactor.presentation.ui.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shifthackz.joyreactor.presentation.entity.ToolbarUI
import com.shifthackz.joyreactor.presentation.entity.asString

@Composable
fun RootToolbarComposable(
    modifier: Modifier = Modifier,
    launchSettings: () -> Unit = {},
) {
    Row(
        modifier = Modifier.padding(all = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        JoyReactorComposable()
        Spacer(modifier = Modifier.weight(1f))
        androidx.compose.material.IconButton(onClick = { launchSettings() }) {
            androidx.compose.material.Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun ToolbarComposable(
    modifier: Modifier = Modifier,
    toolbarUI: ToolbarUI,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    navigateBack: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    if (toolbarUI.isEmpty) return
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = toolbarUI.title.asString(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        colors = colors,
        navigationIcon = {
            if (toolbarUI.backButton) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        Icons.Outlined.ArrowBack,
                        contentDescription = "Back button",
                    )
                }
            }
        },
        actions = actions,
    )
}
