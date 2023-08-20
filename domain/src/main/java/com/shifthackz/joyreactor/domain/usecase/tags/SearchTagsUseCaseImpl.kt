package com.shifthackz.joyreactor.domain.usecase.tags

import com.shifthackz.joyreactor.domain.nsfw.NsfwFilter
import com.shifthackz.joyreactor.domain.repository.TagsRepository
import com.shifthackz.joyreactor.entity.Nsfw
import com.shifthackz.joyreactor.entity.Tag
import kotlinx.coroutines.coroutineScope

class SearchTagsUseCaseImpl(
    private val tagsRepository: TagsRepository,
    private val nsfwFilter: NsfwFilter<Tag>,
) : SearchTagsUseCase {

    override suspend fun invoke(query: String): Result<List<Nsfw<Tag>>> = coroutineScope {
        tagsRepository.search(query).fold(
            onSuccess = { data -> nsfwFilter.filter(data).let(Result.Companion::success) },
            onFailure = Result.Companion::failure,
        )
    }
}
