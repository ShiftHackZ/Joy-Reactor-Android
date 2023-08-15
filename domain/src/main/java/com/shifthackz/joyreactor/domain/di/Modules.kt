package com.shifthackz.joyreactor.domain.di

import com.shifthackz.joyreactor.domain.usecase.comment.FetchPostCommentsUseCase
import com.shifthackz.joyreactor.domain.usecase.comment.FetchPostCommentsUseCaseImpl
import com.shifthackz.joyreactor.domain.usecase.post.FetchPostsPageUseCase
import com.shifthackz.joyreactor.domain.usecase.post.FetchPostsPageUseCaseImpl
import com.shifthackz.joyreactor.domain.usecase.post.GetFullPostUseCase
import com.shifthackz.joyreactor.domain.usecase.post.GetFullPostUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::FetchPostsPageUseCaseImpl) bind FetchPostsPageUseCase::class
    factoryOf(::GetFullPostUseCaseImpl) bind GetFullPostUseCase::class
    factoryOf(::FetchPostCommentsUseCaseImpl) bind FetchPostCommentsUseCase::class
}
