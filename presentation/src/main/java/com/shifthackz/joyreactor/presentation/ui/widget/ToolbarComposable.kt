@file:OptIn(ExperimentalMaterial3Api::class)

package com.shifthackz.joyreactor.presentation.ui.widget

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.shifthackz.joyreactor.presentation.entity.ToolbarUI
import com.shifthackz.joyreactor.presentation.entity.asString

@Composable
fun ToolbarComposable(
    modifier: Modifier = Modifier,
    toolbarUI: ToolbarUI,
    onNavigateBack: () -> Unit = {},
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
        navigationIcon = {
            if (toolbarUI.backButton) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        Icons.Outlined.ArrowBack,
                        contentDescription = "Back button",
                    )
                }
            }
        }
    )
}
