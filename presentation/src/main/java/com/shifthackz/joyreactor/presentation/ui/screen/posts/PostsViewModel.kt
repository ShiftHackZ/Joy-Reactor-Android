package com.shifthackz.joyreactor.presentation.ui.screen.posts

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shifthackz.joyreactor.domain.usecase.nsfw.ObserveNsfwFilterUseCase
import com.shifthackz.joyreactor.domain.usecase.post.FetchPostsPageUseCase
import com.shifthackz.joyreactor.presentation.entity.ToolbarUI
import com.shifthackz.joyreactor.presentation.mvi.MviStateViewModel
import com.shifthackz.joyreactor.presentation.ui.paging.PostsPagingSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PostsViewModel(
    private val url: String,
    private val fetchPostsPageUseCase: FetchPostsPageUseCase,
    private val observeNsfwFilterUseCase: ObserveNsfwFilterUseCase,
) : MviStateViewModel<PostsState>() {

    override val emptyState = PostsState(
        toolbarUI = ToolbarUI.fromUrl(url)
    )

    private val config = PagingConfig(
        pageSize = 10,
        initialLoadSize = 10,
        enablePlaceholders = false,
    )

    private val pager: Pager<String, PostsUI> = Pager(
        config = config,
        initialKey = url,
        pagingSourceFactory = { PostsPagingSource(fetchPostsPageUseCase) { url } },
    )

    val pagingFlow: Flow<PagingData<PostsUI>> = pager.flow.cachedIn(GlobalScope)

    init {
        viewModelScope.launch {
            observeNsfwFilterUseCase().collect {
                setState(currentState.copy(shouldRefresh = true))
            }
        }
    }

    fun refreshComplete() = setState(currentState.copy(shouldRefresh = false))
}
