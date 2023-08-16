package com.shifthackz.joyreactor.presentation.ui.screen.section

import androidx.lifecycle.viewModelScope
import com.shifthackz.joyreactor.domain.usecase.sections.FetchSectionsUseCase
import com.shifthackz.joyreactor.presentation.entity.asTextUi
import com.shifthackz.joyreactor.presentation.mvi.MviStateViewModel
import kotlinx.coroutines.launch

class SectionViewModel(
    private val fetchSectionsUseCase: FetchSectionsUseCase,
) : MviStateViewModel<SectionState>() {

    override val emptyState = SectionState.Loading

    init {
        viewModelScope.launch {
            fetchSectionsUseCase()
                .let {
                    it.fold(
                        onSuccess = {
                            SectionState.Content(it)
                        },
                        onFailure = { t ->
                            SectionState.Error(t.message.toString().asTextUi())
                        }
                    )
                }
                .let(::setState)
        }
    }
}
