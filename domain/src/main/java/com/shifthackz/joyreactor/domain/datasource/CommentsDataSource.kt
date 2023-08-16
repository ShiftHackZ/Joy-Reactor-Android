package com.shifthackz.joyreactor.domain.datasource

import com.shifthackz.joyreactor.entity.Comment

sealed interface CommentsDataSource {

    interface Remote {
        suspend fun fetchComments(postId: String): Result<List<Comment>>
    }
}
