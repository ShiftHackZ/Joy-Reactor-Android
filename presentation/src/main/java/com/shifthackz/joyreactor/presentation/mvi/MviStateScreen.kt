@file:Suppress("unused")

package com.shifthackz.joyreactor.presentation.mvi

abstract class MviStateScreen<S : MviState>(
    viewModel: MviViewModel<S, EmptyEffect>,
) : MviScreen<S, EmptyEffect>(viewModel)
