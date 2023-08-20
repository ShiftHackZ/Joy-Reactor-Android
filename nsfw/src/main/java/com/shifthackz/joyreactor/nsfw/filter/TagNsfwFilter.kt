package com.shifthackz.joyreactor.nsfw.filter

import com.shifthackz.joyreactor.domain.preference.PreferenceManager
import com.shifthackz.joyreactor.entity.Nsfw
import com.shifthackz.joyreactor.entity.Tag
import com.shifthackz.joyreactor.nsfw.core.CoreNsfwFilter
import com.shifthackz.joyreactor.nsfw.core.NsfwGuard

internal class TagNsfwFilter(
    preferenceManager: PreferenceManager,
) : CoreNsfwFilter<Tag>(preferenceManager) {

    override suspend fun evaluate(data: List<Tag>): List<Nsfw<Tag>> {
        return data
            .map { tag -> tag to NsfwGuard.isNsfw(tag.name) }
            .map { (tag, nsfw) -> if (nsfw) Nsfw.Censored else Nsfw.Safe(tag) }
    }
}
