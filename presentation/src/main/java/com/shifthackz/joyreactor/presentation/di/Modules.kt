package com.shifthackz.joyreactor.presentation.di

import org.koin.dsl.module
import com.shifthackz.joyreactor.presentation.ui.screen.test.PostsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

val viewModelModule = module {
    viewModel { parameters ->
        PostsViewModel(parameters.get(), get())
    }
}
