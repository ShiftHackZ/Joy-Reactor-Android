package com.shifthackz.joyreactor.data.datasource.remote

import com.shifthackz.joyreactor.domain.datasource.PostsDataSource
import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Post
import com.shifthackz.joyreactor.network.parser.PostsParser

internal class PostsRemoteDataSource(
    private val postsParser: PostsParser,
) : PostsDataSource.Remote {

    override suspend fun fetchPage(url: String): Result<PagePayload<Post>> {
        return postsParser.fetchPage(url)
    }
}
