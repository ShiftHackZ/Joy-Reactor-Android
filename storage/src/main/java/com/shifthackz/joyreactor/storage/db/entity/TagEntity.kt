package com.shifthackz.joyreactor.storage.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shifthackz.joyreactor.storage.db.contract.TagContract

@Entity(tableName = TagContract.TABLE)
data class TagEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = TagContract.NAME)
    val name: String,
    @ColumnInfo(name = TagContract.URL)
    val url: String,
)
