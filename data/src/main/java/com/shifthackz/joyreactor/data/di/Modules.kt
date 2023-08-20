package com.shifthackz.joyreactor.data.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.shifthackz.joyreactor.data.datasource.local.PostsLocalDataSource
import com.shifthackz.joyreactor.data.datasource.local.TagsLocalDataSource
import com.shifthackz.joyreactor.data.datasource.remote.CommentsRemoteDataSource
import com.shifthackz.joyreactor.data.datasource.remote.PostsRemoteDataSource
import com.shifthackz.joyreactor.data.datasource.remote.SectionsRemoteDataSource
import com.shifthackz.joyreactor.data.datasource.remote.TagsRemoteDataSource
import com.shifthackz.joyreactor.data.preference.PreferenceManagerImpl
import com.shifthackz.joyreactor.data.repository.CommentsRepositoryImpl
import com.shifthackz.joyreactor.data.repository.PostsRepositoryImpl
import com.shifthackz.joyreactor.data.repository.SectionsRepositoryImpl
import com.shifthackz.joyreactor.data.repository.TagsRepositoryImpl
import com.shifthackz.joyreactor.domain.datasource.CommentsDataSource
import com.shifthackz.joyreactor.domain.datasource.PostsDataSource
import com.shifthackz.joyreactor.domain.datasource.SectionsDataSource
import com.shifthackz.joyreactor.domain.datasource.TagsDataSource
import com.shifthackz.joyreactor.domain.preference.PreferenceManager
import com.shifthackz.joyreactor.domain.repository.CommentsRepository
import com.shifthackz.joyreactor.domain.repository.PostsRepository
import com.shifthackz.joyreactor.domain.repository.SectionsRepository
import com.shifthackz.joyreactor.domain.repository.TagsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.io.File

internal val preferenceModule = module {
    single<PreferenceManager> {

        val cc = PreferenceDataStoreFactory.create(null, emptyList(), CoroutineScope(Dispatchers.IO + Job())
        ) {
            File(androidContext().filesDir, "datastore/preferences.preferences_pb")
        }
        PreferenceManagerImpl(cc)
    }
}

internal val remoteDataSourceModule = module {
    factoryOf(::PostsRemoteDataSource) bind PostsDataSource.Remote::class
    factoryOf(::CommentsRemoteDataSource) bind CommentsDataSource.Remote::class
    factoryOf(::SectionsRemoteDataSource) bind SectionsDataSource.Remote::class
    factoryOf(::TagsRemoteDataSource) bind TagsDataSource.Remote::class
}

internal val localDataSourceModule = module {
    factoryOf(::PostsLocalDataSource) bind PostsDataSource.Local::class
    factoryOf(::TagsLocalDataSource) bind TagsDataSource.Local::class
}

internal val repositoryModule = module {
    factoryOf(::PostsRepositoryImpl) bind PostsRepository::class
    factoryOf(::CommentsRepositoryImpl) bind CommentsRepository::class
    factoryOf(::SectionsRepositoryImpl) bind SectionsRepository::class
    factoryOf(::TagsRepositoryImpl) bind TagsRepository::class
}

val dataModule =
    (preferenceModule + remoteDataSourceModule + localDataSourceModule + repositoryModule).toTypedArray()
