package com.shifthackz.joyreactor.storage.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.shifthackz.joyreactor.storage.db.contract.PostContract
import com.shifthackz.joyreactor.storage.db.dto.FullPostDto
import com.shifthackz.joyreactor.storage.db.entity.PostEntity
import com.shifthackz.joyreactor.storage.db.relation.PostToTagRelation

@Dao
abstract class PostDao {

    @Query("SELECT * FROM ${PostContract.TABLE}")
    @Transaction
    abstract suspend fun queryAll(): List<FullPostDto>

    @Query("SELECT * FROM ${PostContract.TABLE} WHERE ${PostContract.ID} = :id")
    @Transaction
    abstract suspend fun queryById(id: String): FullPostDto

    @Upsert
    abstract suspend fun upsert(item: PostEntity)

    @Upsert
    abstract suspend fun upsertList(items: List<PostEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun joinToTag(relation: PostToTagRelation)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun joinToTags(relations: List<PostToTagRelation>)

    @Query("DELETE FROM ${PostContract.TABLE}")
    abstract suspend fun deleteAll()
}
