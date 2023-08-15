package com.shifthackz.joyreactor.domain.usecase

import com.shifthackz.joyreactor.domain.repository.PostsRepository
import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Post

internal class FetchPostsPageUseCaseImpl(
    private val postsRepository: PostsRepository,
) : FetchPostsPageUseCase {

    override suspend fun invoke(url: String): PagePayload<Post> {
        return postsRepository.fetchPage(url)
    }
}
