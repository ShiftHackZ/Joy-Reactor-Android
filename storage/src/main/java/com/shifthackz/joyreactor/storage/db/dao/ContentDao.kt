package com.shifthackz.joyreactor.storage.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.shifthackz.joyreactor.storage.db.contract.ContentContract
import com.shifthackz.joyreactor.storage.db.entity.ContentEntity

@Dao
interface ContentDao {

    @Query("SELECT * FROM ${ContentContract.TABLE}")
    suspend fun queryAll(): List<ContentEntity>

    @Query("SELECT * FROM ${ContentContract.TABLE} WHERE ${ContentContract.ID} = :id")
    suspend fun queryById(id: String): ContentEntity

    @Upsert
    suspend fun upsert(item: ContentEntity)

    @Upsert
    suspend fun upsertList(items: List<ContentEntity>)

    @Query("DELETE FROM ${ContentContract.TABLE}")
    suspend fun deleteAll()
}
