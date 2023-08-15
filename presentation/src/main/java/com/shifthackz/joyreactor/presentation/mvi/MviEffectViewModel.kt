@file:Suppress("unused")

package com.shifthackz.joyreactor.presentation.mvi

abstract class MviEffectViewModel<E : MviEffect> : MviViewModel<EmptyState, E>() {

    override val emptyState = EmptyState

    override fun setState(state: EmptyState) = Unit
}
