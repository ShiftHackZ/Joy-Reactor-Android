package com.shifthackz.joyreactor.domain.repository

import com.shifthackz.joyreactor.entity.Comment

interface CommentsRepository {
    suspend fun fetchComments(postId: String): List<Comment>
}
