package com.shifthackz.joyreactor.presentation.ui.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shifthackz.joyreactor.presentation.entity.TextUI
import com.shifthackz.joyreactor.presentation.entity.asString

@Composable
fun ChipComposable(
    modifier: Modifier = Modifier,
    selected: Boolean,
    text: TextUI,
    icon: ImageVector? = null
) {
    Surface(
        modifier = modifier,
        color = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
        contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
        ),
    ) {
        val textString = text.asString()
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon?.let {
                Icon(
                    modifier = Modifier
                        .size(
                            textString.takeIf(String::isEmpty)?.let { 20.dp } ?: 16.dp
                        )
                    ,
                    imageVector = icon,
                    contentDescription = null,
                )
            }
            textString.takeIf(String::isNotBlank)?.let { str ->
                Text(
                    modifier = Modifier
                        .padding(start = icon?.let { 2.dp } ?: 0.dp)
                        .padding(bottom = 2.dp),
                    text = str,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}
