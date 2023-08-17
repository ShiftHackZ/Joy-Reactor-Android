package com.shifthackz.joyreactor.domain.datasource

import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Tag

sealed interface TagsDataSource {

    interface Remote : TagsDataSource {
        suspend fun fetchPage(url: String): Result<PagePayload<Tag>>
        suspend fun searchTags(query: String): Result<List<Tag>>
    }

    interface Local : TagsDataSource {
        suspend fun saveTags(tags: List<Tag>)
        suspend fun searchTags(query: String): Result<List<Tag>>
    }
}
