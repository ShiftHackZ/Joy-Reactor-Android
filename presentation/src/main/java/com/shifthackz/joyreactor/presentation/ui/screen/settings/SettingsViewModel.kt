package com.shifthackz.joyreactor.presentation.ui.screen.settings

import androidx.lifecycle.viewModelScope
import com.shifthackz.joyreactor.domain.usecase.settings.GetSettingsUseCase
import com.shifthackz.joyreactor.domain.usecase.settings.UpdateSettingsUseCase
import com.shifthackz.joyreactor.presentation.mvi.MviStateViewModel
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase,
) : MviStateViewModel<SettingsState>() {

    override val emptyState = SettingsState.Loading

    init {
        viewModelScope.launch {
            getSettingsUseCase().fold(
                onSuccess = { settings -> setState(SettingsState.Content(settings)) },
                onFailure = { t ->
                    t.printStackTrace()
                }
            )
        }
    }

    fun updateNsfwFilterSetting(value: Boolean) = (currentState as? SettingsState.Content)
        ?.let { state ->
            val settings = state.settings.copy(nsfw = value)
            state.copy(settings = settings)
        }
        ?.also(::setState)
        ?.let { (settings) -> viewModelScope.launch { updateSettingsUseCase(settings) } }
}
