package com.shifthackz.joyreactor.domain.usecase.tags

import com.shifthackz.joyreactor.domain.repository.TagsRepository
import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Tag

internal class FetchTagsUseCaseImpl(
    private val tagsRepository: TagsRepository,
) : FetchTagsUseCase {

    override suspend fun invoke(url: String): Result<PagePayload<Tag>> {
        return tagsRepository.fetchPage(url)
    }
}
