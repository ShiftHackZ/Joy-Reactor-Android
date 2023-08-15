package com.shifthackz.joyreactor.storage.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shifthackz.joyreactor.storage.db.contract.AuthorContract

@Entity(tableName = AuthorContract.TABLE)
data class AuthorEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = AuthorContract.NAME)
    val name: String = "",
    @ColumnInfo(name = AuthorContract.AVATAR_URL)
    val avatarUrl: String = "",
)
