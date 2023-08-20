package com.shifthackz.joyreactor.domain.usecase.settings

import com.shifthackz.joyreactor.domain.preference.PreferenceManager
import com.shifthackz.joyreactor.entity.Settings
import kotlinx.coroutines.coroutineScope

internal class UpdateSettingsUseCaseImpl(
    private val preferenceManager: PreferenceManager,
) : UpdateSettingsUseCase {

    override suspend fun invoke(settings: Settings) = coroutineScope {
        preferenceManager.setNsfwFilter(settings.nsfw)
    }
}
