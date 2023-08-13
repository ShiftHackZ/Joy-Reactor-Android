package com.shifthackz.joyreactor.data

import android.util.Log
import com.shifthackz.joyreactor.domain.entity.Author
import com.shifthackz.joyreactor.domain.entity.Content
import com.shifthackz.joyreactor.domain.entity.PagePayload
import com.shifthackz.joyreactor.domain.entity.Post
import com.shifthackz.joyreactor.domain.entity.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class BasePostGetter {

    suspend fun stub(url: String): PagePayload<Post> = withContext(Dispatchers.IO) {
        val posts = mutableListOf<Post>()
//        val doc = Jsoup.connect("https://joyreactor.cc/all").get()
        val doc = Jsoup.connect(url).get()
        val postContainers = doc.select(".postContainer")
        for (i in 0 until postContainers.size) {
            val postContainer = postContainers[i];
            val contentContainer = postContainer.select(".post_content")[0].select("div")
            val tagsContainer = postContainer.select(".taglist").select("a")

            val contents = mutableListOf<Content>()
            val tags = mutableListOf<Tag>()
            val author = Author(
                name = postContainer.select(".offline_username").text(),
                avatarUrl = postContainer
                    .select("img.avatar")
                    .attr("src")
                    .let(::formatImageUrl),
            )

            val rating = postContainer.select(".post_rating").text().toDoubleOrNull() ?: 0.0

            for (j in 0 until tagsContainer.size) {
                val tagItem = tagsContainer[j]
                val tag = Tag(tagItem.attr("title"), tagItem.attr("href"))
                tags.add(tag)
            }
            for (j in 0 until contentContainer.size) {
                val contentItem = contentContainer[j]
                when {
                    contentItem.hasClass("image") -> {
                        val imageContainer = contentItem.select(".image")
                        val imageUrl = imageContainer.select("a").attr("href")
                        if (imageUrl.isNotBlank()) {
                            contents.add(
                                Content.Image(formatImageUrl(imageUrl))
                            )
                        }
                    }
                }
            }
            if (contents.isNotEmpty()) {
                val post = Post(
                    id = postContainer.attr("id").replace("postContainer", ""),
                    author = author,
                    contents = contents.toSet().toList(),
                    tags = tags,
                    rating = rating,
                )
                posts.add(post)
            }
        }
        val next = doc.select("a.next").attr("href")
        val prev = doc.select("a.prev").attr("href")
        PagePayload(
            data = posts,
            next = next.takeIf { it.isNotEmpty() }?.let { "https://joyreactor.cc$it" },
            prev = prev.takeIf { it.isNotEmpty() }?.let { "https://joyreactor.cc$it" },
        )
    }

    private fun formatImageUrl(input: String): String {
        return if (input.startsWith("//")) "https:$input" else input
    }
}
