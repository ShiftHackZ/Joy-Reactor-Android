package com.shifthackz.joyreactor.domain.usecase.tags

import com.shifthackz.joyreactor.domain.nsfw.NsfwFilter
import com.shifthackz.joyreactor.domain.repository.TagsRepository
import com.shifthackz.joyreactor.entity.Nsfw
import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Tag
import kotlinx.coroutines.coroutineScope

internal class FetchTagsUseCaseImpl(
    private val tagsRepository: TagsRepository,
    private val nsfwFilter: NsfwFilter<Tag>,
) : FetchTagsUseCase {

    override suspend fun invoke(url: String): Result<PagePayload<Nsfw<Tag>>> = coroutineScope {
        tagsRepository.fetchPage(url).fold(
            onSuccess = { payload ->
                Result.success(
                    PagePayload(
                        nsfwFilter.filter(payload.data),
                        payload.next,
                        payload.prev,
                    )
                )
            },
            onFailure = Result.Companion::failure,
        )
    }
}
