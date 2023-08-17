@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.shifthackz.joyreactor.presentation.ui.screen.section

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shifthackz.joyreactor.presentation.R
import com.shifthackz.joyreactor.presentation.mvi.MviStateScreen
import com.shifthackz.joyreactor.presentation.ui.screen.posts.PostsActionsListener
import com.shifthackz.joyreactor.presentation.ui.widget.JoyReactorComposable
import com.shifthackz.joyreactor.presentation.ui.widget.SectionComposable

class SectionScreen(
    private val viewModel: SectionViewModel,
    private val listener: PostsActionsListener,
) : MviStateScreen<SectionState>(viewModel) {

    @Composable
    override fun Content() {
        ScreenContent(
            state = viewModel.state.collectAsStateWithLifecycle().value,
            listener = listener,
            onSearchQueryChanged = viewModel::onSearchQueryChanged,
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    state: SectionState,
    listener: PostsActionsListener = PostsActionsListener.empty,
    onSearchQueryChanged: (String) -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Box(modifier = Modifier.padding(all = 8.dp)) {
                JoyReactorComposable()
            }
        }
    ) { paddingValues ->
        when (state) {
            is SectionState.Content -> Column(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
            ) {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    query = state.searchQuery,
                    onQueryChange = onSearchQueryChanged,
                    onSearch = {},
                    active = state.searchActive,
                    onActiveChange = {},
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    placeholder = { Text(text = stringResource(id = R.string.tag_search)) },
                    trailingIcon = state.takeIf { it.searchQuery.isNotEmpty() }?.let {
                        {
                            Icon(
                                modifier = Modifier.clickable { onSearchQueryChanged("") },
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                            )
                        }
                    },
                ) {}
                Box(modifier = Modifier.height(10.dp))
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    when {
                        state.searchQuery.isNotBlank() && state.searchResults.isNotEmpty() -> {
                            FlowRow(
                                modifier = Modifier
                                    .padding(all = 8.dp)
                                    .padding(bottom = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                state.searchResults.forEach { tag ->
                                    Text(
                                        modifier = Modifier
                                            .padding(vertical = 4.dp)
                                            .background(
                                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                                shape = RoundedCornerShape(4.dp),
                                            )
                                            .clickable { listener.openPosts(tag.url) }
                                            .padding(all = 2.dp),
                                        text = tag.name,
                                    )
                                }
                            }
                        }
                        else -> state.sections.forEach {
                            SectionComposable(
                                section = it,
                                listener = listener,
                            )
                        }
                    }

                    Box(modifier = Modifier.height(100.dp))
                }
            }
            is SectionState.Error -> {}
            SectionState.Loading -> {}
        }
    }
}
