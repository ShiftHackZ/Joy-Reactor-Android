package com.shifthackz.joyreactor.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shifthackz.joyreactor.storage.db.JoyReactorDb.Companion.DB_VERSION
import com.shifthackz.joyreactor.storage.db.dao.AuthorDao
import com.shifthackz.joyreactor.storage.db.dao.ContentDao
import com.shifthackz.joyreactor.storage.db.dao.PostDao
import com.shifthackz.joyreactor.storage.db.dao.TagDao
import com.shifthackz.joyreactor.storage.db.entity.AuthorEntity
import com.shifthackz.joyreactor.storage.db.entity.ContentEntity
import com.shifthackz.joyreactor.storage.db.entity.PostEntity
import com.shifthackz.joyreactor.storage.db.entity.TagEntity
import com.shifthackz.joyreactor.storage.db.relation.PostToTagRelation

@Database(
    version = DB_VERSION,
    exportSchema = true,
    entities = [
        AuthorEntity::class,
        ContentEntity::class,
        PostEntity::class,
        TagEntity::class,
        PostToTagRelation::class,
    ],
)
abstract class JoyReactorDb : RoomDatabase() {
    abstract val authorDao: AuthorDao
    abstract val postDao: PostDao
    abstract val tagDao: TagDao
    abstract val contentDao: ContentDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "joy_reactor_db"
    }
}
