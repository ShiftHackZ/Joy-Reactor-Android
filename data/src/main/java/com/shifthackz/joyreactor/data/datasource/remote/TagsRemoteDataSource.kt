package com.shifthackz.joyreactor.data.datasource.remote

import com.shifthackz.joyreactor.domain.datasource.TagsDataSource
import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Tag
import com.shifthackz.joyreactor.network.ApiImpl
import com.shifthackz.joyreactor.network.parser.TagsParser

internal class TagsRemoteDataSource(
    private val tagsParser: TagsParser,
    private val api: ApiImpl,
) : TagsDataSource.Remote {

    override suspend fun fetchPage(url: String): Result<PagePayload<Tag>> {
        return tagsParser.fetchTags(url)
    }

    override suspend fun searchTags(query: String): Result<List<Tag>> {
        return api.searchTag(query).let {
            Result.success(it.map {
                Tag(it, "https://joyreactor.cc/tag/$it", "")
            })
        } ?: Result.success(emptyList())
    }
}
