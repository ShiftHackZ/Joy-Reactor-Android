package com.shifthackz.joyreactor.presentation.ui.screen.section

import com.shifthackz.joyreactor.entity.Section
import com.shifthackz.joyreactor.presentation.entity.TextUI
import com.shifthackz.joyreactor.presentation.mvi.MviState

sealed interface SectionState : MviState {

    object Loading : SectionState

    data class Content(val sections: List<Section>) : SectionState

    data class Error(val text: TextUI) : SectionState
}
