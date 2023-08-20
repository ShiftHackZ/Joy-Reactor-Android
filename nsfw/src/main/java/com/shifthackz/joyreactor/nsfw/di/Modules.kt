package com.shifthackz.joyreactor.nsfw.di

import com.shifthackz.joyreactor.domain.nsfw.NsfwFilter
import com.shifthackz.joyreactor.entity.Post
import com.shifthackz.joyreactor.entity.Section
import com.shifthackz.joyreactor.entity.Tag
import com.shifthackz.joyreactor.entity.postQualifier
import com.shifthackz.joyreactor.entity.sectionQualifier
import com.shifthackz.joyreactor.entity.tagQualifier
import com.shifthackz.joyreactor.nsfw.filter.PostNsfwFilter
import com.shifthackz.joyreactor.nsfw.filter.SectionNsfwFilter
import com.shifthackz.joyreactor.nsfw.filter.TagNsfwFilter
import org.koin.dsl.module

val nsfwModule = module {

    factory<NsfwFilter<Tag>>(tagQualifier) {
        TagNsfwFilter(get())
    }

    factory<NsfwFilter<Post>>(postQualifier) {
        PostNsfwFilter(get())
    }

    factory<NsfwFilter<Section>>(sectionQualifier) {
        SectionNsfwFilter(get())
    }
}
