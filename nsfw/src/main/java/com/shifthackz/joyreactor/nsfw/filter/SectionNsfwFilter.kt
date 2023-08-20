package com.shifthackz.joyreactor.nsfw.filter

import com.shifthackz.joyreactor.domain.preference.PreferenceManager
import com.shifthackz.joyreactor.entity.Nsfw
import com.shifthackz.joyreactor.entity.Section
import com.shifthackz.joyreactor.nsfw.core.CoreNsfwFilter
import com.shifthackz.joyreactor.nsfw.core.NsfwGuard

internal class SectionNsfwFilter (
    preferenceManager: PreferenceManager,
): CoreNsfwFilter<Section>(preferenceManager) {

    override suspend fun evaluate(data: List<Section>): List<Nsfw<Section>> {
        return data
            .map { section -> section to NsfwGuard.isNsfw(section.name) }
            .map { (section, nsfw) -> if (nsfw) Nsfw.Censored else Nsfw.Safe(section) }
    }
}
