package com.shifthackz.joyreactor.presentation.ui.screen.test

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shifthackz.joyreactor.entity.Post
import com.shifthackz.joyreactor.presentation.mvi.EmptyEffect
import com.shifthackz.joyreactor.presentation.mvi.MviViewModel
import com.shifthackz.joyreactor.domain.repository.PostsRepository
import com.shifthackz.joyreactor.presentation.entity.ToolbarUI
import com.shifthackz.joyreactor.presentation.ui.paging.PostsPagingSource
import kotlinx.coroutines.flow.Flow

class PostsViewModel(
    private val url: String,
    private val postsRepository: PostsRepository,
) : MviViewModel<PostsState, EmptyEffect>() {

    override val emptyState = PostsState(
        toolbarUI = ToolbarUI.fromUrl(url)
    )

    private val config = PagingConfig(
        pageSize = 10,
        initialLoadSize = 10,
    )

    private val pager: Pager<String, Post> = Pager(
        config = config,
        initialKey = url,
        pagingSourceFactory = { PostsPagingSource(postsRepository, url) },
    )

    val pagingFlow: Flow<PagingData<Post>> = pager.flow

    init {
        Log.d("VM", "URL: $url")
    }
}
