package com.shifthackz.joyreactor.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.shifthackz.joyreactor.presentation.ui.screen.test.TestScreenViewModel

val viewModelModule = module {
    viewModelOf(::TestScreenViewModel)
}
