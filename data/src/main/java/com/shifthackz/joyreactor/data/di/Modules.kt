package com.shifthackz.joyreactor.data.di

import com.shifthackz.joyreactor.data.BasePostGetter
import com.shifthackz.joyreactor.data.PostsRemoteDataSource
import com.shifthackz.joyreactor.data.repository.PostsRepositoryImpl
import com.shifthackz.joyreactor.domain.datasource.PostsDataSource
import com.shifthackz.joyreactor.domain.repository.PostsRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val remoteDataSourceModule = module {
    single { BasePostGetter() }
    factoryOf(::PostsRemoteDataSource) bind PostsDataSource.Remote::class
}

internal val repositoryModule = module {
    factoryOf(::PostsRepositoryImpl) bind PostsRepository::class
}

val dataModule = (remoteDataSourceModule + repositoryModule).toTypedArray()
