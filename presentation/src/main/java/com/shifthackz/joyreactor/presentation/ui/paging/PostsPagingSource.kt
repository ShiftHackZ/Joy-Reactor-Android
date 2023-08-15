package com.shifthackz.joyreactor.presentation.ui.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shifthackz.joyreactor.domain.usecase.FetchPostsPageUseCase
import com.shifthackz.joyreactor.entity.JoyReactorLink
import com.shifthackz.joyreactor.entity.Post

class PostsPagingSource(
    private val fetchPostsPageUseCase: FetchPostsPageUseCase,
    private val firstKey: String = JoyReactorLink.HOME_NEW.url,
) : PagingSource<String, Post>() {

//    override val keyReuseSupported: Boolean = true

    override fun getRefreshKey(state: PagingState<String, Post>): String {
        return firstKey
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Post> {
        return fetchPostsPageUseCase(params.key ?: firstKey).let { page ->
            LoadResult.Page(
                data = page.data,
                prevKey = page.prev,
                nextKey = page.next
            )
        }
    }

    init {
        Log.d("VM", "URL: $firstKey")
    }
}
