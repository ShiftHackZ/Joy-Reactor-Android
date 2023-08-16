package com.shifthackz.joyreactor.domain.usecase.sections

import com.shifthackz.joyreactor.domain.repository.SectionsRepository
import com.shifthackz.joyreactor.entity.Section

internal class FetchSectionsUseCaseImpl(
    private val sectionsRepository: SectionsRepository,
) : FetchSectionsUseCase {

    override suspend fun invoke(): Result<List<Section>> {
        return sectionsRepository.fetchSections()
    }
}
