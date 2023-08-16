package com.shifthackz.joyreactor.data.repository

import com.shifthackz.joyreactor.domain.datasource.PostsDataSource
import com.shifthackz.joyreactor.domain.repository.PostsRepository
import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Post
import kotlinx.coroutines.coroutineScope

class PostsRepositoryImpl(
    private val postsRds: PostsDataSource.Remote,
    private val postsLds: PostsDataSource.Local,
) : PostsRepository {

    override suspend fun fetchPage(url: String): Result<PagePayload<Post>> = coroutineScope {
        return@coroutineScope postsRds.fetchPage(url).getOrNull()?.let {
            postsLds.savePosts(it.data)
            Result.success(it)
        } ?: Result.failure(IllegalStateException())
    }

    override suspend fun getPost(id: String): Result<Post> {
        return postsLds.getPost(id)
    }
}
