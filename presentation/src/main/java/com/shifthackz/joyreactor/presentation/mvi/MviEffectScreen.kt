@file:Suppress("unused")

package com.shifthackz.joyreactor.presentation.mvi

abstract class MviEffectScreen<E : MviEffect>(
    viewModel: MviViewModel<EmptyState, E>,
) : MviScreen<EmptyState, E>(viewModel)
