package com.shifthackz.joyreactor.data

import com.shifthackz.joyreactor.domain.datasource.PostsDataSource
import com.shifthackz.joyreactor.domain.entity.PagePayload
import com.shifthackz.joyreactor.domain.entity.Post

class PostsRemoteDataSource(
    private val basePostGetter: BasePostGetter,
) : PostsDataSource.Remote {

    override suspend fun stub(url: String): PagePayload<Post> {
        return basePostGetter.stub(url)
    }
}
