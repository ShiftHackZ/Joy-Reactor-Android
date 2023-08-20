package com.shifthackz.joyreactor.domain.usecase.sections

import com.shifthackz.joyreactor.domain.nsfw.NsfwFilter
import com.shifthackz.joyreactor.domain.repository.SectionsRepository
import com.shifthackz.joyreactor.entity.Nsfw
import com.shifthackz.joyreactor.entity.Section
import kotlinx.coroutines.coroutineScope

internal class FetchSectionsUseCaseImpl(
    private val sectionsRepository: SectionsRepository,
    private val nsfwFilter: NsfwFilter<Section>,
) : FetchSectionsUseCase {

    override suspend fun invoke(): Result<List<Nsfw<Section>>> = coroutineScope {
        sectionsRepository.fetchSections().fold(
            onSuccess = { data -> nsfwFilter.filter(data).let(Result.Companion::success) },
            onFailure = Result.Companion::failure,
        )
    }
}
