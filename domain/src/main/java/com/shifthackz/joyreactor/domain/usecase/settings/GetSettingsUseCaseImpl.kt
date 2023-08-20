package com.shifthackz.joyreactor.domain.usecase.settings

import com.shifthackz.joyreactor.domain.preference.PreferenceManager
import com.shifthackz.joyreactor.entity.Settings
import kotlinx.coroutines.flow.first

internal class GetSettingsUseCaseImpl(
    private val preferenceManager: PreferenceManager,
) : GetSettingsUseCase {

    override suspend fun invoke(): Result<Settings> {
        return Result.success(
            Settings(
                preferenceManager.getNsfwFilter().first(),
            )
        )
    }
}
