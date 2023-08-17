@file:OptIn(ExperimentalMaterial3Api::class)

package com.shifthackz.joyreactor.presentation.ui.screen.tags

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.shifthackz.joyreactor.entity.Tag
import com.shifthackz.joyreactor.presentation.entity.ToolbarUI
import com.shifthackz.joyreactor.presentation.extensions.rememberLazyListState
import com.shifthackz.joyreactor.presentation.ui.screen.posts.PostsActionsListener
import com.shifthackz.joyreactor.presentation.ui.widget.TagComposable
import com.shifthackz.joyreactor.presentation.ui.widget.ToolbarComposable
import kotlinx.coroutines.flow.Flow

@Composable
fun TagsScreen(
    modifier: Modifier = Modifier,
    pagingFlow: Flow<PagingData<Tag>>,
    postsActionsListener: PostsActionsListener = PostsActionsListener.empty,
    navigateBack: () -> Unit = {},
) {
    val lazyTags = pagingFlow.collectAsLazyPagingItems()
    val listState = lazyTags.rememberLazyListState()
    Scaffold(
        modifier = modifier,
        topBar = {
            ToolbarComposable(
                toolbarUI = ToolbarUI(backButton = true),
                navigateBack = navigateBack,
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(
                items = lazyTags,
                key = { tag -> System.currentTimeMillis() + tag.hashCode() },
            ) { tag ->
                tag?.let {
                    TagComposable(
                        modifier = Modifier.fillMaxWidth(),
                        tag = it,
                        onClick = { postsActionsListener.openPosts(it.url) }
                    )
                }
            }
            item { Box(modifier = Modifier.height(100.dp)) }
        }
    }
}
