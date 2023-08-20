package com.shifthackz.joyreactor.domain.usecase.settings

import com.shifthackz.joyreactor.entity.Settings

interface GetSettingsUseCase {
    suspend operator fun invoke(): Result<Settings>
}
