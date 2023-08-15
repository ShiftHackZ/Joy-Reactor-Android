package com.shifthackz.joyreactor.storage.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.shifthackz.joyreactor.storage.db.contract.ContentContract
import com.shifthackz.joyreactor.storage.db.contract.PostContract

@Entity(
    tableName = ContentContract.TABLE,
    foreignKeys = [
        ForeignKey(
            entity = PostEntity::class,
            parentColumns = [PostContract.ID],
            childColumns = [ContentContract.POST_ID],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index(value = [ContentContract.POST_ID])
    ],
)
data class ContentEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = ContentContract.ID)
    val id: String,
    @ColumnInfo(name = ContentContract.TYPE)
    val type: String,
    @ColumnInfo(name = ContentContract.VALUE)
    val value: String,
    @ColumnInfo(name = ContentContract.POST_ID)
    val postId: String,
)
