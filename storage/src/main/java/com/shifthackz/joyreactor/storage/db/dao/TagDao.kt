package com.shifthackz.joyreactor.storage.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.shifthackz.joyreactor.storage.db.contract.TagContract
import com.shifthackz.joyreactor.storage.db.entity.TagEntity

@Dao
interface TagDao {

    @Query("SELECT * FROM ${TagContract.TABLE}")
    suspend fun queryAll(): List<TagEntity>

    @Query("SELECT * FROM ${TagContract.TABLE} WHERE ${TagContract.NAME} = :name")
    suspend fun queryByName(name: String): TagEntity

    @Query("SELECT * FROM ${TagContract.TABLE} WHERE ${TagContract.NAME} LIKE '%' || :query || '%'")
    suspend fun search(query: String): List<TagEntity>

    @Upsert
    suspend fun upsert(item: TagEntity)

    @Upsert
    suspend fun upsertList(items: List<TagEntity>)

    @Query("DELETE FROM ${TagContract.TABLE}")
    suspend fun deleteAll()
}
