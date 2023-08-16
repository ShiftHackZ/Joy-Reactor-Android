package com.shifthackz.joyreactor.storage.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.shifthackz.joyreactor.storage.db.contract.AuthorContract
import com.shifthackz.joyreactor.storage.db.contract.PostContract

@Entity(
    tableName = PostContract.TABLE,
    foreignKeys = [
        ForeignKey(
            entity = AuthorEntity::class,
            parentColumns = [AuthorContract.NAME],
            childColumns = [PostContract.AUTHOR_NAME],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index(value = [PostContract.AUTHOR_NAME])
    ],
)
data class PostEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = PostContract.ID)
    val id: String = "",
    @ColumnInfo(name = PostContract.RATING)
    val rating: Double = 0.0,
    @ColumnInfo(name = PostContract.ESTIMATED_COMMENTS_COUNT)
    val estimatedCommentsCount: Int = 0,
    @ColumnInfo(name = PostContract.DATE)
    val date: Long = 0L,
    @ColumnInfo(name = PostContract.URL)
    val url: String = "",
    @ColumnInfo(name = PostContract.AUTHOR_NAME)
    val authorName: String = "",
)
