package com.shifthackz.joyreactor.nsfw.core

object NsfwGuard {

    private val nsfwExplicitWords = arrayOf(
        "nsfw",
        "porn",
        "ero",
        "pussy",
        "anime ero",
        "эротика",
        "эротики",
        "сиськи",
        "сисечки",
        "грудь",
        "эро",
        "попа",
        "писечка",
        "бобрик",
        "нейросиськи",
        "барышни",
        "арт-ню",
        "барышня",
    )

    fun isNsfw(input: String?): Boolean {
        if (input.isNullOrBlank()) return false
        val words = input.split(" ").map(String::lowercase)
        for (word in words) {
            if (nsfwExplicitWords.contains(word)) return true
        }
        return false
    }

    fun isNsfw(input: List<String?>?): Boolean {
        if (input.isNullOrEmpty()) return false
        val words = input
            .map { it.toString() }
            .map { it.split(" ") }
            .flatten()
            .map(String::lowercase)
        for (word in words) {
            if (nsfwExplicitWords.contains(word)) return true
        }
        return false
    }
}
