package com.shifthackz.joyreactor.network.parser

import com.shifthackz.joyreactor.entity.Author
import com.shifthackz.joyreactor.entity.Comment
import com.shifthackz.joyreactor.entity.Content
import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Post
import com.shifthackz.joyreactor.entity.Tag
import com.shifthackz.joyreactor.network.extensions.formatImageUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class PostsParser {

    suspend fun fetchPage(url: String): PagePayload<Post> = withContext(Dispatchers.IO) {
        val posts = mutableListOf<Post>()
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
                    .let(String::formatImageUrl),
            )
            val comments = mutableListOf<Comment>()

            val rating = postContainer.select(".post_rating").text().toDoubleOrNull() ?: 0.0

            for (j in 0 until tagsContainer.size) {
                val tagItem = tagsContainer[j]
                val tag = Tag(tagItem.attr("title"), tagItem.attr("href"))
                tags.add(tag)
            }
            for (j in 0 until contentContainer.size) {
                if (j == 0) continue
                val contentItem = contentContainer[j]
//                Log.d("DBG0", "[$j] $contentItem")
                if (contentItem.select("h3").size > 0) {
                    val header3 = contentItem.select("h3").text()
                    if (header3.isNotBlank()) contents.add(Content.Header(header3))
                }
                if (contentItem.select("p").size > 0) {
                    for (k in 0 until contentItem.select("p").size) {
                        val text = contentItem.select("p")[k].text()
                        if (text.isNotBlank()) contents.add(Content.Text(text))
                    }
                }
                if (contentItem.hasClass("image")) {
                    val imageContainer = contentItem.select(".image")
                    val imageUrlFromA = imageContainer.select("a").attr("href")
                    val imageUrlFromImg = imageContainer.select("img").attr("src")
                    val videoUrl = imageContainer.select("video").select("source").attr("src")
                    when {
                        imageUrlFromA.isNotBlank() -> contents.add(
                            Content.Image(imageUrlFromA.formatImageUrl())
                        )
                        imageUrlFromImg.isNotBlank() && videoUrl.isBlank() -> contents.add(
                            Content.Image(imageUrlFromImg.formatImageUrl())
                        )
                        imageUrlFromImg.isNotBlank() && videoUrl.isNotBlank() -> contents.add(
                            Content.Video(videoUrl.formatImageUrl())
                        )
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
                    comments = comments,
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

}
