package com.shifthackz.joyreactor.presentation.ui.screen.posts.search

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shifthackz.joyreactor.domain.usecase.nsfw.ObserveNsfwFilterUseCase
import com.shifthackz.joyreactor.domain.usecase.post.FetchPostsPageUseCase
import com.shifthackz.joyreactor.presentation.mvi.MviStateViewModel
import com.shifthackz.joyreactor.presentation.ui.paging.PostsPagingSource
import com.shifthackz.joyreactor.presentation.ui.screen.posts.PostsUI
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val fetchPostsPageUseCase: FetchPostsPageUseCase,
    private val observeNsfwFilterUseCase: ObserveNsfwFilterUseCase,
) : MviStateViewModel<SearchState>() {

    override val emptyState = SearchState()

    private val config = PagingConfig(
        pageSize = 10,
        initialLoadSize = 10,
        enablePlaceholders = false,
    )

    private val pager: Pager<String, PostsUI> = Pager(
        config = config,
        initialKey = "https://joyreactor.cc/search?q=",
        pagingSourceFactory = { PostsPagingSource(fetchPostsPageUseCase) {
            "https://joyreactor.cc/search?q=${currentState.searchQuery}"
        } },
    )

    val pagingFlow: Flow<PagingData<PostsUI>> = pager.flow.cachedIn(GlobalScope)

    init {
        viewModelScope.launch {
            observeNsfwFilterUseCase().collect {
            }
        }
    }

    fun onSearchQueryChanged(query: String) = currentState
        .copy(searchQuery = query)
        .also(::setState)
}
