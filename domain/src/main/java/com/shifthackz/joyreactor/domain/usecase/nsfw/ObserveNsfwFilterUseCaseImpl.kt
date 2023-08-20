package com.shifthackz.joyreactor.domain.usecase.nsfw

import com.shifthackz.joyreactor.domain.preference.PreferenceManager
import kotlinx.coroutines.flow.Flow

internal class ObserveNsfwFilterUseCaseImpl(
    private val preferenceManager: PreferenceManager,
): ObserveNsfwFilterUseCase {

    override suspend fun invoke(): Flow<Boolean> = preferenceManager.getNsfwFilter()
}
