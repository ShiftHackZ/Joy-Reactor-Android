@file:OptIn(ExperimentalMaterial3Api::class)

package com.shifthackz.joyreactor.presentation.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shifthackz.joyreactor.presentation.R
import com.shifthackz.joyreactor.presentation.entity.ToolbarUI
import com.shifthackz.joyreactor.presentation.entity.asTextUi
import com.shifthackz.joyreactor.presentation.mvi.MviStateScreen
import com.shifthackz.joyreactor.presentation.ui.widget.SettingsItem
import com.shifthackz.joyreactor.presentation.ui.widget.ToolbarComposable

class SettingsScreen(
    private val viewModel: SettingsViewModel,
    private val navigateBack: () -> Unit = {},
) : MviStateScreen<SettingsState>(viewModel) {

    @Composable
    override fun Content() {
        SettingsScreenContent(
            modifier = Modifier.fillMaxSize(),
            state = viewModel.state.collectAsStateWithLifecycle().value,
            navigateBack = navigateBack,
            onNsfwFilterChanged = viewModel::updateNsfwFilterSetting,
        )
    }
}

@Composable
private fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    state: SettingsState,
    navigateBack: () -> Unit = {},
    onNsfwFilterChanged: (Boolean) -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            ToolbarComposable(
                toolbarUI = ToolbarUI(
                    backButton = true,
                    title = R.string.settings.asTextUi(),
                ),
                navigateBack = navigateBack,
            )
        }
    ) { paddingValues ->
        when (state) {
            is SettingsState.Content -> Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(paddingValues),
            ) {
                SettingsItem(
                    text = R.string.nsfw_filter.asTextUi(),
                    onClick = { onNsfwFilterChanged(!state.settings.nsfw) },
                    endValueContent = {
                        Switch(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            checked = state.settings.nsfw,
                            onCheckedChange = onNsfwFilterChanged,
                        )
                    },
                )
            }
            SettingsState.Loading -> Unit
        }
    }
}
