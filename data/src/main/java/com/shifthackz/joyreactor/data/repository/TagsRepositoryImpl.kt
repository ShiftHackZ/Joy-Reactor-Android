package com.shifthackz.joyreactor.data.repository

import com.shifthackz.joyreactor.domain.datasource.TagsDataSource
import com.shifthackz.joyreactor.domain.repository.TagsRepository
import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Tag
import kotlinx.coroutines.coroutineScope

internal class TagsRepositoryImpl(
    private val tagsRds: TagsDataSource.Remote,
    private val tagsLds: TagsDataSource.Local,
) : TagsRepository {

    override suspend fun fetchPage(url: String): Result<PagePayload<Tag>> = coroutineScope {
        tagsRds.fetchPage(url).fold(
            onSuccess = {
                tagsLds.saveTags(it.data)
                Result.success(it)
            },
            onFailure = Result.Companion::failure,
        )
    }

    override suspend fun search(query: String): Result<List<Tag>> = coroutineScope {
        val remoteTags = tagsRds.searchTags(query).getOrThrow()
        val localTags = tagsLds.searchTags(query).getOrThrow()
        Result.success(listOf(remoteTags, localTags).flatten())
    }
}
