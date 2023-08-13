package com.shifthackz.joyreactor.domain.datasource

import com.shifthackz.joyreactor.domain.entity.PagePayload
import com.shifthackz.joyreactor.domain.entity.Post

sealed interface PostsDataSource {

    interface Remote : PostsDataSource {
        suspend fun stub(url: String): PagePayload<Post>
    }
}
