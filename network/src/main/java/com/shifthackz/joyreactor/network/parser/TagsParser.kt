package com.shifthackz.joyreactor.network.parser

import android.util.Log
import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Tag
import com.shifthackz.joyreactor.network.extensions.baseUrl
import com.shifthackz.joyreactor.network.extensions.formatImageUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class TagsParser {

    suspend fun fetchTags(url: String): Result<PagePayload<Tag>> = withContext(Dispatchers.IO) {
        try {
            val tags = mutableListOf<Tag>()

            val doc = Jsoup.newSession()
                .url(url)
                .get()

            val location = doc.location()
            val baseUrl = location.baseUrl()

            val itemContainers = doc.select(".blog_list_item")

            for (i in 0 until itemContainers.size) {
                val itemContainer = itemContainers[i]
                val name = itemContainer.select(".blog_list_name").select("a").attr("title").split("(").first().trim()
                val link = itemContainer.select(".blog_list_name").select("a").attr("href")
                val image = itemContainer.select(".blog_list_avatar").select("img").attr("src").formatImageUrl()
                Log.d("TAGD", "img = ${image}")
                val tag = Tag(
                    name,
                    link,
                    image,
                )
                tags.add(tag)
            }

            val next = doc.select("a.next").attr("href")
            val prev = doc.select("a.prev").attr("href")
            val payload = PagePayload(
                data = tags,
                next = next.takeIf { it.isNotEmpty() }?.let { "$baseUrl$it" },
                prev = prev.takeIf { it.isNotEmpty() }?.let { "$baseUrl$it" },
            )

            Result.success(payload)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
