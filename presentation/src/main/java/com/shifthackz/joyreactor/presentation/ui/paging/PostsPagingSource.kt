package com.shifthackz.joyreactor.presentation.ui.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shifthackz.joyreactor.domain.usecase.post.FetchPostsPageUseCase
import com.shifthackz.joyreactor.entity.JoyReactorLink
import com.shifthackz.joyreactor.entity.Post
import com.shifthackz.joyreactor.presentation.ui.screen.posts.PostsUI

class PostsPagingSource(
    private val fetchPostsPageUseCase: FetchPostsPageUseCase,
    private val firstKey: () -> String = { JoyReactorLink.HOME_NEW.url },
) : PagingSource<String, PostsUI>() {

    override val keyReuseSupported: Boolean = true

    private val uniqueKeys = mutableListOf<String>()

    init {
        registerInvalidatedCallback {
            uniqueKeys.clear()
        }
    }

    override fun getRefreshKey(state: PagingState<String, PostsUI>): String {
        return firstKey()
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PostsUI> {
        val key = params.key ?: firstKey()
        return fetchPostsPageUseCase(key).let { result ->
            result.fold(
                onSuccess = { page ->
                    Log.d("PAGING", "[$key] ${page.data.joinToString(", ") { "P(${it.id})" }}")
                    Log.d("DBGN", "PPP [$key] prev = ${page.prev} ||| next = ${page.next}")
                    LoadResult.Page(
                        data = appendUnique(page.data),
                        prevKey = page.prev,
                        nextKey = page.next
                    )
                },
                onFailure = { t -> LoadResult.Error(t) },
            )
        }
    }

    private fun appendUnique(items: List<Post>): List<PostsUI> = items.map { post ->
        if (uniqueKeys.contains(post.id)) PostsUI.NonUnique
        else {
            uniqueKeys.add(post.id)
            PostsUI.Ok(post)
        }
    }

    init {
        Log.d("VM", "URL: $firstKey")
    }
}
