package com.shifthackz.joyreactor.presentation.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shifthackz.joyreactor.domain.entity.Post
import com.shifthackz.joyreactor.domain.repository.PostsRepository

class PostsPagingSource(
    private val postsRepository: PostsRepository,
) : PagingSource<String, Post>() {

    override fun getRefreshKey(state: PagingState<String, Post>): String? {
        return FIRST_KEY
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Post> {
//        val pageSize = params.loadSize
        val pageNext = params.key ?: FIRST_KEY
        return postsRepository
            .stub(pageNext)
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
