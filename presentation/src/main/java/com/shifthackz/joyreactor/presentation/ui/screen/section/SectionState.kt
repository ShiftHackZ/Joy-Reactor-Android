package com.shifthackz.joyreactor.presentation.ui.screen.section

import com.shifthackz.joyreactor.entity.Nsfw
import com.shifthackz.joyreactor.entity.Section
import com.shifthackz.joyreactor.entity.Tag
import com.shifthackz.joyreactor.presentation.entity.TextUI
import com.shifthackz.joyreactor.presentation.mvi.MviState

sealed interface SectionState : MviState {

    object Uninitialized : SectionState

    object Loading : SectionState

    data class Content(
        val sections: List<Nsfw<Section>>,
        val searchActive: Boolean = false,
        val searchRunning: Boolean = false,
        val searchQuery: String = "",
        val searchResults: List<Tag> = emptyList(),
    ) : SectionState

    data class Error(val text: TextUI) : SectionState
}
