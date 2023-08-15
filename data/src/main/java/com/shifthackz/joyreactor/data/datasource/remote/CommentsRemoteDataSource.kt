package com.shifthackz.joyreactor.data.datasource.remote

import com.shifthackz.joyreactor.domain.datasource.CommentsDataSource
import com.shifthackz.joyreactor.entity.Comment
import com.shifthackz.joyreactor.network.parser.CommentsParser

class CommentsRemoteDataSource(
    private val commentsParser: CommentsParser,
) : CommentsDataSource.Remote {

    override suspend fun fetchComments(postId: String): List<Comment> {
        return commentsParser.fetchComments(postId)
    }
}
