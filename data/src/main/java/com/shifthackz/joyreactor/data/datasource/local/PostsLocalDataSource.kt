package com.shifthackz.joyreactor.data.datasource.local

import com.shifthackz.joyreactor.data.mappers.toContentEntities
import com.shifthackz.joyreactor.data.mappers.toEntity
import com.shifthackz.joyreactor.domain.datasource.PostsDataSource
import com.shifthackz.joyreactor.entity.Author
import com.shifthackz.joyreactor.entity.Post
import com.shifthackz.joyreactor.entity.Tag
import com.shifthackz.joyreactor.storage.db.dao.AuthorDao
import com.shifthackz.joyreactor.storage.db.dao.ContentDao
import com.shifthackz.joyreactor.storage.db.dao.PostDao
import com.shifthackz.joyreactor.storage.db.dao.TagDao
import com.shifthackz.joyreactor.storage.db.relation.PostToTagRelation
import kotlinx.coroutines.coroutineScope

internal class PostsLocalDataSource(
    private val postDao: PostDao,
    private val contentDao: ContentDao,
    private val authorDao: AuthorDao,
    private val tagDao: TagDao,
) : PostsDataSource.Local {

    override suspend fun savePosts(posts: List<Post>) = coroutineScope {
        val authors = posts.map(Post::author)
        val tags = posts.map(Post::tags).flatten().toSet().toList()
        val tagsRelations = posts
            .map { it to it.tags }
            .map { (post, tags) ->
                tags.map { tag ->  PostToTagRelation(post.id, tag.name) }
            }
            .flatten()

        saveAuthors(authors)
        saveTags(tags)
        postDao.upsertList(posts.map(Post::toEntity))
        postDao.joinToTags(tagsRelations)
        contentDao.upsertList(posts.map(Post::toContentEntities).flatten())
    }

    private suspend fun saveAuthors(authors: List<Author>) {
        return authorDao.upsertList(authors.map(Author::toEntity))
    }

    private suspend fun saveTags(tags: List<Tag>) {
        return tagDao.upsertList(tags.map(Tag::toEntity))
    }
}
