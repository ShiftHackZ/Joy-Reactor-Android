package com.shifthackz.joyreactor.data.repository

import com.shifthackz.joyreactor.domain.datasource.PostsDataSource
import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Post
import com.shifthackz.joyreactor.domain.repository.PostsRepository
import kotlinx.coroutines.coroutineScope

class PostsRepositoryImpl(
    private val postsRds: PostsDataSource.Remote,
    private val postsLds: PostsDataSource.Local,
) : PostsRepository {

    override suspend fun fetchPage(url: String): PagePayload<Post> = coroutineScope {
        val page = postsRds.fetchPage(url)
        postsLds.savePosts(page.data)
        return@coroutineScope page
    }
}
