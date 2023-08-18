@file:OptIn(ExperimentalMaterial3Api::class)

package com.shifthackz.joyreactor.presentation.ui.screen.posts.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.shifthackz.joyreactor.presentation.R
import com.shifthackz.joyreactor.presentation.mvi.MviStateScreen
import com.shifthackz.joyreactor.presentation.ui.screen.posts.PostsActionsListener
import com.shifthackz.joyreactor.presentation.ui.screen.posts.PostsScreenContent
import com.shifthackz.joyreactor.presentation.ui.screen.posts.PostsUI
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SearchScreen(
    private val viewModel: SearchViewModel,
    private val postsActionsListener: PostsActionsListener,
) : MviStateScreen<SearchState>(viewModel) {

    @Composable
    override fun Content() {
        SearchScreenContent(
            modifier = Modifier.fillMaxSize(),
            state = viewModel.state.collectAsStateWithLifecycle().value,
            pagingFlow = viewModel.pagingFlow,
            listener = postsActionsListener,
            onSearchQueryChanged = viewModel::onSearchQueryChanged,
        )
    }
}

@Composable
private fun SearchScreenContent(
    modifier: Modifier = Modifier,
    state: SearchState = SearchState(),
    pagingFlow: Flow<PagingData<PostsUI>>,
    listener: PostsActionsListener = PostsActionsListener.empty,
    onSearchQueryChanged: (String) -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val searchJob = remember { mutableStateOf<Job?>(null) }
    val lazyPosts = pagingFlow.collectAsLazyPagingItems()
    val emptyStatePredicate: () -> Boolean = {
        lazyPosts.loadState.refresh is LoadState.NotLoading
                && lazyPosts.itemCount == 0
                && lazyPosts.loadState.append.endOfPaginationReached
    }
    val loadingPredicate: () -> Boolean = {
        lazyPosts.loadState.refresh is LoadState.Loading
    }
    Column(modifier) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            query = state.searchQuery,
            onQueryChange = { query ->
                searchJob.value?.cancel()
                searchJob.value = scope.launch {
                    onSearchQueryChanged(query)
                    delay(500L)
                    lazyPosts.refresh()
                }
            },
            onSearch = {},
            active = false,
            onActiveChange = {},
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            placeholder = { Text(text = stringResource(id = R.string.post_search)) },
            trailingIcon = {
                when {
                    state.searchQuery.isNotEmpty() && loadingPredicate() -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    state.searchQuery.isNotEmpty() && !loadingPredicate() -> {
                        Icon(
                            modifier = Modifier.clickable {
                                searchJob.value?.cancel()
                                onSearchQueryChanged("")
                                lazyPosts.refresh()
                            },
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                        )
                    }
                }
            },
        ) {}
        Box(modifier = Modifier.height(10.dp))
        Box {
            PostsScreenContent(
                lazyPosts = lazyPosts,
                postsActionsListener = listener,
            )

            @Composable
            fun WrapUpperLayer(content: @Composable () -> Unit) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(8.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    content()
                }
            }

            when {
                state.searchQuery.isEmpty() -> WrapUpperLayer {
                    Text("Type your keyword in the search field to start searching")
                }

                state.searchQuery.isNotEmpty() && emptyStatePredicate() -> WrapUpperLayer {
                    Icon(
                        imageVector = Icons.Default.SearchOff,
                        contentDescription = null,
                    )
                    Text(text = "Nothing found.")
                }
            }
        }
    }
}
