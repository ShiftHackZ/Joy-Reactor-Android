package com.shifthackz.joyreactor.entity

sealed interface Nsfw<out T : Any> {
    object Censored : Nsfw<Nothing>
    data class Safe<out T : Any>(val data: T) : Nsfw<T>
}
