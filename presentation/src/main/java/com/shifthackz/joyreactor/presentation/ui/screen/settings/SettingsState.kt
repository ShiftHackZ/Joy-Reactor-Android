package com.shifthackz.joyreactor.presentation.ui.screen.settings

import com.shifthackz.joyreactor.entity.Settings
import com.shifthackz.joyreactor.presentation.mvi.MviState

sealed interface SettingsState : MviState {
    object Loading : SettingsState
    data class Content(val settings: Settings) : SettingsState
}
