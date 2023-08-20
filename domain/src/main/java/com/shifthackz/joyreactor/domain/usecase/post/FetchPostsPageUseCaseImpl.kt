package com.shifthackz.joyreactor.domain.usecase.post

import com.shifthackz.joyreactor.domain.nsfw.NsfwFilter
import com.shifthackz.joyreactor.domain.repository.PostsRepository
import com.shifthackz.joyreactor.entity.Nsfw
import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Post
import kotlinx.coroutines.coroutineScope

internal class FetchPostsPageUseCaseImpl(
    private val postsRepository: PostsRepository,
    private val nsfwFilter: NsfwFilter<Post>,
) : FetchPostsPageUseCase {

    override suspend fun invoke(url: String): Result<PagePayload<Nsfw<Post>>> = coroutineScope {
        postsRepository.fetchPage(url).fold(
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
