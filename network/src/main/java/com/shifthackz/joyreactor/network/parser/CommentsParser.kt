package com.shifthackz.joyreactor.network.parser

import com.shifthackz.joyreactor.entity.Author
import com.shifthackz.joyreactor.entity.Comment
import com.shifthackz.joyreactor.network.extensions.formatImageUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class CommentsParser {

    suspend fun fetchComments(postId: String): List<Comment> = withContext(Dispatchers.IO) {
        val comments = mutableListOf<Comment>()
        val doc = Jsoup.connect("https://joyreactor.cc/post/comments/$postId").get()
        val commentsContainer = doc.select(".comment_list_post")[0].select("div.comment")
        val commentListsContainer = doc.select(".comment_list_post")[0].select("div.comment_list")



        for (i in 0 until commentsContainer.size) {
            val id = commentsContainer[i].attr("id").replace("comment", "")
//            val hasP = doc.select("#comment_list_comment_${id}")
//            Log.d("DBG11", "[$i] ${commentsContainer[i]}")
            val parentId = commentsContainer[i].parent()
                ?.attr("id")
                ?.takeIf { it.contains("comment_list_comment") }
                ?.replace("comment_list_comment_", "")

            val text = commentsContainer[i].select(".txt")[0].select("div")[1].text()

            val userAvatar = commentsContainer[i]
                .select("img.avatar")
                .attr("src")
                .let(String::formatImageUrl)

            val userName = commentsContainer[i]
                .select("a.comment_username")
                .text()

//            Log.d("DBG11", "[$i] $id <-> ${parentId}")
//            Log.d("DBG11", "[$i] $text")
//            Log.d("DBG11", "[$i] $userName")

            val rating = commentsContainer[i]
                .select("span.comment_rating")
                .text()
                .toDoubleOrNull() ?: 0.0

            val comment = Comment(
                id = id,
                parentId = parentId,
                text = text,
                author = Author(
                    name = userName,
                    avatarUrl = userAvatar,
                ),
                rating = rating,
            )
            comments.add(comment)
        }

//        Log.d("DBG11", "[-=--->] ${commentsContainer.size} VS ${commentListsContainer.size}")

        comments
    }
}