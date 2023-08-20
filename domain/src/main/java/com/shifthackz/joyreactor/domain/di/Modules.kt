package com.shifthackz.joyreactor.domain.di

import com.shifthackz.joyreactor.domain.usecase.comment.FetchPostCommentsUseCase
import com.shifthackz.joyreactor.domain.usecase.comment.FetchPostCommentsUseCaseImpl
import com.shifthackz.joyreactor.domain.usecase.nsfw.ObserveNsfwFilterUseCase
import com.shifthackz.joyreactor.domain.usecase.nsfw.ObserveNsfwFilterUseCaseImpl
import com.shifthackz.joyreactor.domain.usecase.post.FetchPostsPageUseCase
import com.shifthackz.joyreactor.domain.usecase.post.FetchPostsPageUseCaseImpl
import com.shifthackz.joyreactor.domain.usecase.post.GetFullPostUseCase
import com.shifthackz.joyreactor.domain.usecase.post.GetFullPostUseCaseImpl
import com.shifthackz.joyreactor.domain.usecase.sections.FetchSectionsUseCase
import com.shifthackz.joyreactor.domain.usecase.sections.FetchSectionsUseCaseImpl
import com.shifthackz.joyreactor.domain.usecase.settings.GetSettingsUseCase
import com.shifthackz.joyreactor.domain.usecase.settings.GetSettingsUseCaseImpl
import com.shifthackz.joyreactor.domain.usecase.settings.ObserveSettingsUseCase
import com.shifthackz.joyreactor.domain.usecase.settings.ObserveSettingsUseCaseImpl
import com.shifthackz.joyreactor.domain.usecase.settings.UpdateSettingsUseCase
import com.shifthackz.joyreactor.domain.usecase.settings.UpdateSettingsUseCaseImpl
import com.shifthackz.joyreactor.domain.usecase.tags.FetchTagsUseCase
import com.shifthackz.joyreactor.domain.usecase.tags.FetchTagsUseCaseImpl
import com.shifthackz.joyreactor.domain.usecase.tags.SearchTagsUseCase
import com.shifthackz.joyreactor.domain.usecase.tags.SearchTagsUseCaseImpl
import com.shifthackz.joyreactor.entity.postQualifier
import com.shifthackz.joyreactor.entity.sectionQualifier
import com.shifthackz.joyreactor.entity.tagQualifier
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetFullPostUseCaseImpl) bind GetFullPostUseCase::class
    factoryOf(::FetchPostCommentsUseCaseImpl) bind FetchPostCommentsUseCase::class
    factoryOf(::GetSettingsUseCaseImpl) bind GetSettingsUseCase::class
    factoryOf(::UpdateSettingsUseCaseImpl) bind UpdateSettingsUseCase::class
    factoryOf(::ObserveSettingsUseCaseImpl) bind ObserveSettingsUseCase::class
    factoryOf(::ObserveNsfwFilterUseCaseImpl) bind ObserveNsfwFilterUseCase::class

    factory<SearchTagsUseCase> {
        SearchTagsUseCaseImpl(get(), get(tagQualifier))
    }

    factory<FetchTagsUseCase> {
        FetchTagsUseCaseImpl(get(), get(tagQualifier))
    }

    factory<FetchSectionsUseCase> {
        FetchSectionsUseCaseImpl(get(), get(sectionQualifier))
    }

    factory<FetchPostsPageUseCase> {
        FetchPostsPageUseCaseImpl(get(), get(postQualifier))
    }
}
