package com.shifthackz.joyreactor.data.datasource.local

import com.shifthackz.joyreactor.data.mappers.toDomain
import com.shifthackz.joyreactor.data.mappers.toEntity
import com.shifthackz.joyreactor.domain.datasource.TagsDataSource
import com.shifthackz.joyreactor.entity.Tag
import com.shifthackz.joyreactor.storage.db.dao.TagDao
import com.shifthackz.joyreactor.storage.db.entity.TagEntity

internal class TagsLocalDataSource(
    private val tagDao: TagDao,
) : TagsDataSource.Local {

    override suspend fun saveTags(tags: List<Tag>) {
        return tagDao.upsertList(tags.map(Tag::toEntity))
    }

    override suspend fun searchTags(query: String): Result<List<Tag>> {
        return tagDao
            .search(query)
            .map(TagEntity::toDomain)
            .let(Result.Companion::success)
    }
}
