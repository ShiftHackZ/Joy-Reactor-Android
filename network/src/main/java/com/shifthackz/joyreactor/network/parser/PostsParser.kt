package com.shifthackz.joyreactor.network.parser

import android.util.Log
import com.shifthackz.joyreactor.entity.Author
import com.shifthackz.joyreactor.entity.Comment
import com.shifthackz.joyreactor.entity.Content
import com.shifthackz.joyreactor.entity.JoyReactorLink
import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Post
import com.shifthackz.joyreactor.entity.Tag
import com.shifthackz.joyreactor.network.extensions.formatImageUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.net.URL
import java.util.Date


class PostsParser {

    private var last: String? = null

    suspend fun fetchPage(url: String): Result<PagePayload<Post>> = withContext(Dispatchers.IO) {
        try {
            val posts = mutableListOf<Post>()
//            val doc = Jsoup.connect(url).get()
            val doc = Jsoup.newSession()
                .url(url)
//                .followRedirects(false)
                .apply {
                    last?.let {
//                        val map = mapOf("Referer" to "$last")
                        val map = mapOf("Referer" to JoyReactorLink.HOME_BEST.url)
                        Log.d("DBGN", "map = $map")
                        headers(map) }
                }
                .get()

            val location = doc.location()
            val locationUrl = URL(location)
            val baseUrl = locationUrl.protocol + "://" + locationUrl.host

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
                val commentsCount =
                    postContainer.select("a.toggleComments").text().replace("Комментарии", "")
                        .trim().toIntOrNull() ?: 0

                val date = Date(postContainer
                    .select("span.non-localized-time")
                    .attr("data-time").toLongOrNull()?.let { it * 1000L } ?: System.currentTimeMillis())

                val postUrl = postContainer
                    .select("a.link")
                    .attr("href")
                    .let { "$baseUrl$it" }

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
                        estimatedCommentsCount = commentsCount,
                        date = date,
                        url = postUrl,
                    )
                    posts.add(post)
                }
            }
            var next = doc.select("a.next").attr("href")
//            val badNext = url.contains(next)
//            if (badNext) {
//                val num = url.split("/").lastOrNull()?.toIntOrNull()?.let {
//                    val cc = next.replace("$it", "") + "${it - 1}"
//                    next = cc
//                }
//            }
            val prev = doc.select("a.prev").attr("href")
            Log.d("DBGN", "LOCATION: $location")
            Log.d("DBGN", "[$url] prev = $prev ||| next = $next")
            val payload = PagePayload(
                data = posts,
                next = next.takeIf { it.isNotEmpty() }?.let { "$baseUrl$it" },
                prev = prev.takeIf { it.isNotEmpty() }?.let { "$baseUrl$it" },
//                prev = prev.takeIf { it.isNotEmpty() }?.let { url },
            )
            last = url
            Result.success(payload)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
