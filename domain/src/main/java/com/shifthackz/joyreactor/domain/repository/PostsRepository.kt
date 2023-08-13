package com.shifthackz.joyreactor.domain.repository

import com.shifthackz.joyreactor.domain.entity.PagePayload
import com.shifthackz.joyreactor.domain.entity.Post

interface PostsRepository {
    suspend fun stub(url: String): PagePayload<Post>
}
