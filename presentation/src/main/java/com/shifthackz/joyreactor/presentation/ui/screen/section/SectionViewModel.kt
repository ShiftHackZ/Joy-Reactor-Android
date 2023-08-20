package com.shifthackz.joyreactor.presentation.ui.screen.section

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.shifthackz.joyreactor.domain.usecase.nsfw.ObserveNsfwFilterUseCase
import com.shifthackz.joyreactor.domain.usecase.sections.FetchSectionsUseCase
import com.shifthackz.joyreactor.domain.usecase.tags.SearchTagsUseCase
import com.shifthackz.joyreactor.entity.Nsfw
import com.shifthackz.joyreactor.entity.Tag
import com.shifthackz.joyreactor.presentation.entity.asTextUi
import com.shifthackz.joyreactor.presentation.mvi.MviStateViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SectionViewModel(
    private val fetchSectionsUseCase: FetchSectionsUseCase,
    private val searchTagsUseCase: SearchTagsUseCase,
    private val observeNsfwFilterUseCase: ObserveNsfwFilterUseCase,
) : MviStateViewModel<SectionState>() {

    override val emptyState = SectionState.Uninitialized

    private var searchJob: Job? = null

    init {
        loadData()
        viewModelScope.launch {
            observeNsfwFilterUseCase().collect { loadData() }
        }
    }

    fun onSearchQueryChanged(query: String) = (currentState as? SectionState.Content)
        ?.copy(searchQuery = query, searchRunning = true)
        ?.also(::setState)
        ?.also { state ->
            searchJob?.cancel()
            if (state.searchQuery.isBlank()) {
                state.copy(searchResults = listOf(), searchRunning = false).let(::setState)
                return@also
            }
            searchJob = viewModelScope.launch {
                delay(500L)
                searchTagsUseCase(state.searchQuery).let { result ->
                    result.fold(
                        onSuccess = { nsfwTags ->
                            Log.d("VM", "Search: $nsfwTags")
                            val data = nsfwTags.filterIsInstance<Nsfw.Safe<Tag>>().map(Nsfw.Safe<Tag>::data)
                            state.copy(searchResults = data, searchRunning = false).let(::setState)
                        },
                        onFailure = {
                            state.copy(searchRunning = false).let(::setState)
                            it.printStackTrace()
                        }
                    )
                }
            }
        }

    private fun loadData() {
        if (currentState is SectionState.Loading) return
        setState(SectionState.Loading)
        viewModelScope.launch {
            fetchSectionsUseCase()
                .let { result ->
                    result.fold(
                        onSuccess = SectionState::Content,
                        onFailure = { t ->
                            SectionState.Error(t.message.toString().asTextUi())
                        }
                    )
                }
                .let(::setState)
        }
    }
}
