package com.shifthackz.joyreactor.presentation.ui.widget

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

private const val TRANSITION_LABEL = "shimmer"

fun Modifier.shimmer(shape: Shape = RectangleShape) = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = TRANSITION_LABEL)
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(animation = tween(durationMillis = 1500)),
        label = TRANSITION_LABEL,
    )
    val colorBg = MaterialTheme.colorScheme.secondaryContainer
        .copy(alpha = 0.5f)
        .compositeOver(MaterialTheme.colorScheme.surface)
    val colorEffect = MaterialTheme.colorScheme.onSurfaceVariant
    background(
        shape = shape,
        brush = Brush.linearGradient(
            colors = listOf(
                colorBg,
                colorEffect,
                colorBg,
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat()),
        )
    ).onGloballyPositioned { coordinates ->
        size = coordinates.size
    }
}
