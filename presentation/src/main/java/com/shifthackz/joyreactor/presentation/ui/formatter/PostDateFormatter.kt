package com.shifthackz.joyreactor.presentation.ui.formatter

import com.shifthackz.joyreactor.presentation.entity.TextUI
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PostDateFormatter {
    operator fun invoke(date: Date): TextUI {
        return TextUI.Static(
            SimpleDateFormat(FORMAT, Locale.getDefault()).format(date)
        )
    }

    companion object {
        private const val FORMAT = "dd.MM.yyyy"
    }
}
