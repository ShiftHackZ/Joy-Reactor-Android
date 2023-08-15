package com.shifthackz.joyreactor.data.repository

import com.shifthackz.joyreactor.domain.datasource.CommentsDataSource
import com.shifthackz.joyreactor.domain.repository.CommentsRepository
import com.shifthackz.joyreactor.entity.Comment

internal class CommentsRepositoryImpl(
    private val commendsRds: CommentsDataSource.Remote,
) : CommentsRepository {

    override suspend fun fetchComments(postId: String): List<Comment> {
        return commendsRds.fetchComments(postId)
    }
}
