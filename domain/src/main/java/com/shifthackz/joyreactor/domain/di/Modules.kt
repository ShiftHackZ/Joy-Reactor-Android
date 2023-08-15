package com.shifthackz.joyreactor.domain.di

import com.shifthackz.joyreactor.domain.usecase.GetPostsPageUseCase
import com.shifthackz.joyreactor.domain.usecase.GetPostsPageUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetPostsPageUseCaseImpl) bind GetPostsPageUseCase::class
}
