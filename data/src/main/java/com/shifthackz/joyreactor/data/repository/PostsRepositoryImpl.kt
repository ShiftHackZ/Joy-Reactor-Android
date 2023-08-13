package com.shifthackz.joyreactor.data.repository

import com.shifthackz.joyreactor.domain.datasource.PostsDataSource
import com.shifthackz.joyreactor.domain.entity.PagePayload
import com.shifthackz.joyreactor.domain.entity.Post
import com.shifthackz.joyreactor.domain.repository.PostsRepository

class PostsRepositoryImpl(
    private val postsRds: PostsDataSource.Remote,
) : PostsRepository {

    override suspend fun stub(url: String): PagePayload<Post> {
        return postsRds.stub(url)
    }
}
