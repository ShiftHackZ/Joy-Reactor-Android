package com.shifthackz.joyreactor.presentation.ui.screen.test

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shifthackz.joyreactor.domain.entity.Post
import com.shifthackz.joyreactor.presentation.mvi.EmptyEffect
import com.shifthackz.joyreactor.presentation.mvi.EmptyState
import com.shifthackz.joyreactor.presentation.mvi.MviViewModel
import com.shifthackz.joyreactor.domain.repository.PostsRepository
import com.shifthackz.joyreactor.presentation.ui.paging.PostsPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TestScreenViewModel(
    private val postsRepository: PostsRepository,
) : MviViewModel<EmptyState, EmptyEffect>() {

    override val emptyState = EmptyState

    private val config = PagingConfig(
        pageSize = 10,
        initialLoadSize = 10,
    )

    private val pager: Pager<String, Post> = Pager(
        config = config,
        initialKey = PostsPagingSource.FIRST_KEY,
        pagingSourceFactory = { PostsPagingSource(postsRepository) },
    )

    val pagingFlow: Flow<PagingData<Post>> = pager.flow

    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            val posts = postsRepository.stub()
//            Log.d("DBG0", "${posts}")
//        }
    }
}
