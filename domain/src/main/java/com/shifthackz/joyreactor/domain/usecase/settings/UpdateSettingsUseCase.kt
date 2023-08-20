package com.shifthackz.joyreactor.domain.usecase.settings

import com.shifthackz.joyreactor.entity.Settings

interface UpdateSettingsUseCase {
    suspend operator fun invoke(settings: Settings)
}
