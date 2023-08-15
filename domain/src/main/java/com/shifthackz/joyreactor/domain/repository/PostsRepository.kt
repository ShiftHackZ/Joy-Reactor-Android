package com.shifthackz.joyreactor.domain.repository

import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Post

interface PostsRepository {
    suspend fun fetchPage(url: String): PagePayload<Post>
    suspend fun getPost(id: String): Post
}
