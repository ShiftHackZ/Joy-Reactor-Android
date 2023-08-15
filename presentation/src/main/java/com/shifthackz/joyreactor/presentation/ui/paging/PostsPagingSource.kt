package com.shifthackz.joyreactor.presentation.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shifthackz.joyreactor.entity.Post
import com.shifthackz.joyreactor.domain.repository.PostsRepository

class PostsPagingSource(
    private val postsRepository: PostsRepository,
    private val firstKey: String = FIRST_KEY,
) : PagingSource<String, Post>() {

//    override val keyReuseSupported: Boolean = true

    override fun getRefreshKey(state: PagingState<String, Post>): String? {
        return firstKey
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Post> {
        val pageNext = params.key ?: firstKey
        return postsRepository
            .fetchPage(pageNext)
            .let {
                LoadResult.Page(
                    data = it.data,
                    prevKey = it.prev,
                    nextKey = it.next
                )
            }
    }

    companion object {
        const val FIRST_KEY = "https://joyreactor.cc/all"
    }
}
