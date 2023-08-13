package com.shifthackz.joyreactor.data

import com.shifthackz.joyreactor.domain.entity.Content
import com.shifthackz.joyreactor.domain.entity.PagePayload
import com.shifthackz.joyreactor.domain.entity.Post
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
            val contents = mutableListOf<Content>()
            for (j in 0 until contentContainer.size) {
                val cntItem = contentContainer[j]
                when {
                    cntItem.hasClass("image") -> {
                        val imageContainer = cntItem.select(".image")
                        val imageUrl = imageContainer.select("a").attr("href")
                        if (imageUrl.isNotBlank()) {
                            contents.add(
                                Content.Image(
                                    if (imageUrl.startsWith("//")) "https:$imageUrl" else imageUrl
                                )
                            )
                        }
                    }
                }
            }
            if (contents.isNotEmpty()) {
                posts.add(Post("", contents.toSet().toList()))
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
