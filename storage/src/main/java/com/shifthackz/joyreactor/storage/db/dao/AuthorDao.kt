package com.shifthackz.joyreactor.storage.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.shifthackz.joyreactor.storage.db.contract.AuthorContract
import com.shifthackz.joyreactor.storage.db.entity.AuthorEntity

@Dao
interface AuthorDao {

    @Query("SELECT * FROM ${AuthorContract.TABLE}")
    suspend fun queryAll(): List<AuthorEntity>

    @Query("SELECT * FROM ${AuthorContract.TABLE} WHERE ${AuthorContract.NAME} = :name")
    suspend fun queryByName(name: String): AuthorEntity

    @Upsert
    suspend fun upsert(item: AuthorEntity)

    @Upsert
    suspend fun upsertList(items: List<AuthorEntity>)

    @Query("DELETE FROM ${AuthorContract.TABLE}")
    suspend fun deleteAll()
}
