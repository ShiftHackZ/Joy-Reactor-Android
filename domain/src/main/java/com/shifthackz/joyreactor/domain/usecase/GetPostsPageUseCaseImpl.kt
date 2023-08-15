package com.shifthackz.joyreactor.domain.usecase

import com.shifthackz.joyreactor.domain.repository.PostsRepository
import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Post

internal class GetPostsPageUseCaseImpl(
    private val postsRepository: PostsRepository,
) : GetPostsPageUseCase {

    override suspend fun invoke(url: String): PagePayload<Post> {
        return postsRepository.fetchPage(url)
    }
}
