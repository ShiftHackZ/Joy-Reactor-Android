package com.shifthackz.joyreactor.presentation.ui.screen.section

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    state: SectionState,
    listener: PostsActionsListener = PostsActionsListener.empty,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Box(modifier = Modifier.padding(all = 8.dp)) {
                JoyReactorComposable()
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
        ) {
            when (state) {
                is SectionState.Content -> {
                    state.sections.forEach {
                        SectionComposable(
                            section = it,
                            listener = listener,
                        )
                    }
                }
                is SectionState.Error -> {}
                SectionState.Loading -> {}
            }
            Box(modifier = Modifier.height(100.dp))
        }
    }
}
