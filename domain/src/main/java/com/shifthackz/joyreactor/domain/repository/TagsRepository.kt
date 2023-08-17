package com.shifthackz.joyreactor.domain.repository

import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Tag

interface TagsRepository {
    suspend fun fetchPage(url: String): Result<PagePayload<Tag>>
    suspend fun search(query: String): Result<List<Tag>>
}
