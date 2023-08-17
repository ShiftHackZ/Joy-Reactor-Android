package com.shifthackz.joyreactor.domain.usecase.tags

import com.shifthackz.joyreactor.domain.repository.TagsRepository
import com.shifthackz.joyreactor.entity.Tag

class SearchTagsUseCaseImpl(
    private val tagsRepository: TagsRepository,
) : SearchTagsUseCase {

    override suspend fun invoke(query: String): Result<List<Tag>> {
        return tagsRepository.search(query)
    }
}
