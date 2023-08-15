package com.shifthackz.joyreactor.domain.usecase.post

import com.shifthackz.joyreactor.domain.repository.PostsRepository
import com.shifthackz.joyreactor.entity.Post

internal class GetFullPostUseCaseImpl(
    private val postsRepository: PostsRepository,
) : GetFullPostUseCase {

    override suspend fun invoke(id: String): Post {
        return postsRepository.getPost(id)
    }
}
