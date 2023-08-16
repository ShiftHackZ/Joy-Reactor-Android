package com.shifthackz.joyreactor.domain.usecase.sections

import com.shifthackz.joyreactor.entity.Section

interface FetchSectionsUseCase {
    suspend operator fun invoke(): Result<List<Section>>
}
