package com.shifthackz.joyreactor.domain.datasource

import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Post

sealed interface PostsDataSource {

    interface Remote : PostsDataSource {
        suspend fun fetchPage(url: String): Result<PagePayload<Post>>
    }

    interface Local : PostsDataSource {
        suspend fun savePosts(posts: List<Post>)
        suspend fun getPost(id: String): Result<Post>
    }
}
