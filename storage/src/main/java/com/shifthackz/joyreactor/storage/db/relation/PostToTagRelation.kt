package com.shifthackz.joyreactor.storage.db.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.shifthackz.joyreactor.storage.db.contract.PostToTagRelationContract

@Entity(primaryKeys = [PostToTagRelationContract.POST_ID, PostToTagRelationContract.TAG_NAME])
data class PostToTagRelation(
    @ColumnInfo(name = PostToTagRelationContract.POST_ID)
    val postId: String,
    @ColumnInfo(name = PostToTagRelationContract.TAG_NAME)
    val tagName: String,
)
