package com.shifthackz.joyreactor.presentation.ui.screen.test

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.shifthackz.joyreactor.domain.entity.Content
import com.shifthackz.joyreactor.domain.entity.Post
import com.shifthackz.joyreactor.presentation.mvi.EmptyEffect
import com.shifthackz.joyreactor.presentation.mvi.EmptyState
import com.shifthackz.joyreactor.presentation.mvi.MviScreen
import kotlinx.coroutines.flow.Flow

class TestScreen(
    private val viewModel: TestScreenViewModel,
) : MviScreen<EmptyState, EmptyEffect>(viewModel) {

    @Composable
    override fun Content() {
        ScreenContent(
            modifier = Modifier.fillMaxSize(),
            pagingFlow = viewModel.pagingFlow,
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    pagingFlow: Flow<PagingData<Post>>,
) {
    val lazyPosts = pagingFlow.collectAsLazyPagingItems()
    Scaffold(modifier) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(lazyPosts) { post ->
                post?.let {
                    Post(post = it)
                }
            }
        }
    }
}

@Composable
private fun Post(
    modifier: Modifier = Modifier,
    post: Post,
) {
    Column(modifier) {
        post.contents.forEach {
            when (it) {
                is Content.Image -> {
                    AsyncImage(model = it.url, contentDescription = null)
                }
            }
        }
    }
}

