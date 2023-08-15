package com.shifthackz.joyreactor.presentation.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shifthackz.joyreactor.domain.usecase.GetPostsPageUseCase
import com.shifthackz.joyreactor.entity.JoyReactorLink
import com.shifthackz.joyreactor.entity.Post

class PostsPagingSource(
    private val getPostsPageUseCase: GetPostsPageUseCase,
    private val firstKey: String = JoyReactorLink.NEW.url,
) : PagingSource<String, Post>() {

//    override val keyReuseSupported: Boolean = true

    override fun getRefreshKey(state: PagingState<String, Post>): String {
        return firstKey
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Post> {
        return getPostsPageUseCase(params.key ?: firstKey).let { page ->
            LoadResult.Page(
                data = page.data,
                prevKey = page.prev,
                nextKey = page.next
            )
        }
    }
}
