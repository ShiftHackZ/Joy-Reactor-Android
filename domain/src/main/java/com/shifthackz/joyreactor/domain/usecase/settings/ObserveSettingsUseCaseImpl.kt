package com.shifthackz.joyreactor.domain.usecase.settings

import com.shifthackz.joyreactor.domain.preference.PreferenceManager
import com.shifthackz.joyreactor.entity.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ObserveSettingsUseCaseImpl(
    private val preferenceManager: PreferenceManager,
) : ObserveSettingsUseCase {
    override suspend fun invoke(): Flow<Settings> {
        //combine(listOf(flow1,flow2,flow3)){}.collect{}
        return preferenceManager.getNsfwFilter().map {
            Settings(it)
        }
    }
}
