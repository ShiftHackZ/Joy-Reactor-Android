package com.shifthackz.joyreactor.storage.db.dto

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.shifthackz.joyreactor.storage.db.contract.AuthorContract
import com.shifthackz.joyreactor.storage.db.contract.ContentContract
import com.shifthackz.joyreactor.storage.db.contract.PostContract
import com.shifthackz.joyreactor.storage.db.contract.PostToTagRelationContract
import com.shifthackz.joyreactor.storage.db.entity.AuthorEntity
import com.shifthackz.joyreactor.storage.db.entity.ContentEntity
import com.shifthackz.joyreactor.storage.db.entity.PostEntity
import com.shifthackz.joyreactor.storage.db.entity.TagEntity
import com.shifthackz.joyreactor.storage.db.relation.PostToTagRelation

data class FullPostDto(
    @Embedded
    var post: PostEntity = PostEntity(),
    @Relation(
        entity = AuthorEntity::class,
        parentColumn = PostContract.AUTHOR_NAME,
        entityColumn = AuthorContract.NAME,
    )
    var author: AuthorEntity = AuthorEntity(),
    @Relation(
        parentColumn = PostToTagRelationContract.POST_ID,
        entity = PostEntity::class,
        entityColumn = PostContract.ID,
        associateBy = Junction(
            value = PostToTagRelation::class,
            parentColumn = PostToTagRelationContract.POST_ID,
            entityColumn = PostToTagRelationContract.TAG_NAME,
        ),
    )
    var tags: List<TagEntity> = emptyList(),
    @Relation(
        entity = ContentEntity::class,
        parentColumn = PostContract.ID,
        entityColumn = ContentContract.POST_ID,
    )
    var contents: List<ContentEntity> = emptyList(),
)
