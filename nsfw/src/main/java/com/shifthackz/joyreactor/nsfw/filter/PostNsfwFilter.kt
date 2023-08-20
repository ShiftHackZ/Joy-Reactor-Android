package com.shifthackz.joyreactor.nsfw.filter

import com.shifthackz.joyreactor.domain.preference.PreferenceManager
import com.shifthackz.joyreactor.entity.Nsfw
import com.shifthackz.joyreactor.entity.Post
import com.shifthackz.joyreactor.entity.Tag
import com.shifthackz.joyreactor.nsfw.core.CoreNsfwFilter
import com.shifthackz.joyreactor.nsfw.core.NsfwGuard

internal class PostNsfwFilter(
    preferenceManager: PreferenceManager,
): CoreNsfwFilter<Post>(preferenceManager) {

    override suspend fun evaluate(data: List<Post>): List<Nsfw<Post>> {
        return data
            .map { post -> post to NsfwGuard.isNsfw(post.tags.map(Tag::name)) }
            .map { (post, nsfw) -> if (nsfw) Nsfw.Censored else Nsfw.Safe(post) }
    }
}
