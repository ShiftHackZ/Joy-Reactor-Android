@file:Suppress("unused")

package com.shifthackz.joyreactor.presentation.mvi

abstract class MviStateViewModel<S: MviState> : MviViewModel<S, EmptyEffect>() {

    override fun emitEffect(effect: EmptyEffect) = Unit
}
