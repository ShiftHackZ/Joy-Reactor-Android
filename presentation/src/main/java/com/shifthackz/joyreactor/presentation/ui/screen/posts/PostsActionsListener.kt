package com.shifthackz.joyreactor.presentation.ui.screen.posts

import com.shifthackz.joyreactor.entity.Post

interface PostsActionsListener {
    val openPosts: (String) -> Unit
    val openSlider: (Post) -> Unit
    val openComments: (String) -> Unit
    val openWebView: (String) -> Unit
    val sharePostLink: (String) -> Unit

    companion object {
        val empty = object : PostsActionsListener {
            override val openPosts: (String) -> Unit = {}
            override val openSlider: (Post) -> Unit = {}
            override val openComments: (String) -> Unit = {}
            override val openWebView: (String) -> Unit = {}
            override val sharePostLink: (String) -> Unit = {}
        }
    }
}
