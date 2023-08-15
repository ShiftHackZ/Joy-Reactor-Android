@file:OptIn(ExperimentalFoundationApi::class)

package com.shifthackz.joyreactor.presentation.ui.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun PagerIndicatorComposable(
    modifier: Modifier = Modifier,
    size: Int,
    state: PagerState,
) {
    if (size > 1) Row(
        modifier
            .height(16.dp)
            .padding(start = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(size) { iteration ->
            val isActive = state.currentPage == iteration
            val color = MaterialTheme.colorScheme.primary.copy(
                alpha = if (isActive) 1.0f else 0.5f
            )
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size((if (isActive) 8 else 5).dp)
            )
        }
    }
}
