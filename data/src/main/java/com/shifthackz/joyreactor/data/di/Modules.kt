package com.shifthackz.joyreactor.data.di

import com.shifthackz.joyreactor.data.datasource.local.PostsLocalDataSource
import com.shifthackz.joyreactor.data.datasource.remote.CommentsRemoteDataSource
import com.shifthackz.joyreactor.data.datasource.remote.PostsRemoteDataSource
import com.shifthackz.joyreactor.data.repository.CommentsRepositoryImpl
import com.shifthackz.joyreactor.data.repository.PostsRepositoryImpl
import com.shifthackz.joyreactor.domain.datasource.CommentsDataSource
import com.shifthackz.joyreactor.domain.datasource.PostsDataSource
import com.shifthackz.joyreactor.domain.repository.CommentsRepository
import com.shifthackz.joyreactor.domain.repository.PostsRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val remoteDataSourceModule = module {
    factoryOf(::PostsRemoteDataSource) bind PostsDataSource.Remote::class
    factoryOf(::CommentsRemoteDataSource) bind CommentsDataSource.Remote::class
}

internal val localDataSourceModule = module {
    factoryOf(::PostsLocalDataSource) bind PostsDataSource.Local::class
}

internal val repositoryModule = module {
    factoryOf(::PostsRepositoryImpl) bind PostsRepository::class
    factoryOf(::CommentsRepositoryImpl) bind CommentsRepository::class
}

val dataModule = (remoteDataSourceModule + localDataSourceModule + repositoryModule).toTypedArray()
