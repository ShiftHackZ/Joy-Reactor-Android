package com.shifthackz.joyreactor.presentation.ui.screen.posts

import com.shifthackz.joyreactor.entity.Post
import kotlin.random.Random

sealed interface PostsUI {
    fun key(): String

    data class Ok(val post: Post) : PostsUI {
        override fun key(): String {
            return post.id
        }
    }

    object NonUnique : PostsUI {
        override fun key(): String {
            val timestamp = System.currentTimeMillis().toString()
            val random = Random.nextInt(0, 5598).toString()
            val hash = (if (Random.nextBoolean()) timestamp.hashCode() else random.hashCode()).toString()
            val components = listOf(timestamp, random, hash).shuffled()
            val stubs = listOf(".", "_", "+", "c", "z", "a")
            val salt = stubs[Random.nextInt(0, stubs.size)]
            return components.joinToString(salt)
        }
    }
}
