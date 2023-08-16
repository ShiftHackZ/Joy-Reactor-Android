package com.shifthackz.joyreactor.presentation.di

import com.shifthackz.joyreactor.presentation.ui.screen.comments.CommentsViewModel
import com.shifthackz.joyreactor.presentation.ui.screen.posts.PostsViewModel
import com.shifthackz.joyreactor.presentation.ui.screen.section.SectionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { parameters ->
        PostsViewModel(parameters.get(), get())
    }

    viewModel {
        CommentsViewModel(it.get(), get())
    }

    viewModelOf(::SectionViewModel)
}
