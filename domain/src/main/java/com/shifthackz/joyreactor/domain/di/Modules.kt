package com.shifthackz.joyreactor.domain.di

import com.shifthackz.joyreactor.domain.usecase.FetchPostsPageUseCase
import com.shifthackz.joyreactor.domain.usecase.FetchPostsPageUseCaseImpl
import com.shifthackz.joyreactor.domain.usecase.GetFullPostUseCase
import com.shifthackz.joyreactor.domain.usecase.GetFullPostUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::FetchPostsPageUseCaseImpl) bind FetchPostsPageUseCase::class
    factoryOf(::GetFullPostUseCaseImpl) bind GetFullPostUseCase::class
}
