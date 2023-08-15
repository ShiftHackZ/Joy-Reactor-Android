package com.shifthackz.joyreactor.domain.usecase.comment

import com.shifthackz.joyreactor.domain.repository.CommentsRepository
import com.shifthackz.joyreactor.entity.Comment

internal class FetchPostCommentsUseCaseImpl(
    private val commentsRepository: CommentsRepository,
) : FetchPostCommentsUseCase {

    override suspend fun invoke(postId: String): List<Comment> {
        return commentsRepository.fetchComments(postId)
    }
}
