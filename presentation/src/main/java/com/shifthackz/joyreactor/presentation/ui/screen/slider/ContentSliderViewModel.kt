//package com.shifthackz.joyreactor.presentation.ui.screen.slider
//
//import android.util.Log
//import androidx.lifecycle.viewModelScope
//import com.shifthackz.joyreactor.domain.usecase.GetFullPostUseCase
//import com.shifthackz.joyreactor.presentation.mvi.MviStateViewModel
//import kotlinx.coroutines.launch
//
//class ContentSliderViewModel(
////    private val postId: String,
////    private val getFullPostUseCase: GetFullPostUseCase,
//) : MviStateViewModel<ContentSliderState>() {
//
//    override val emptyState = ContentSliderState.Loading
//
//    init {
//        Log.d("VM", "CREATE PostId : $postId")
////        viewModelScope.launch {
////            getFullPostUseCase(postId)
////                .let(ContentSliderState::Content)
////                .let(::setState)
////        }
//    }
//
//    override fun onCleared() {
//        Log.d("VM", "CLEARED PostId : $postId")
//        super.onCleared()
//    }
//}
