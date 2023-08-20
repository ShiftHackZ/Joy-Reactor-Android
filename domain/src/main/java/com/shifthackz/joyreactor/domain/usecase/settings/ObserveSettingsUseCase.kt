package com.shifthackz.joyreactor.domain.usecase.settings

import com.shifthackz.joyreactor.entity.Settings
import kotlinx.coroutines.flow.Flow

interface ObserveSettingsUseCase {
    suspend operator fun invoke(): Flow<Settings>
}
