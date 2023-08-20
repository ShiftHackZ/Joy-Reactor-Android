package com.shifthackz.joyreactor.presentation.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.shifthackz.joyreactor.presentation.entity.TextUI
import com.shifthackz.joyreactor.presentation.entity.asString
import com.shifthackz.joyreactor.presentation.ui.theme.OrangeSecondary50

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    text: TextUI,
    endValueContent: (@Composable () -> Unit)? = null,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = OrangeSecondary50)
            .defaultMinSize(minHeight = 50.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
//            modifier = endValueContent
//                ?.let { Modifier.fillMaxWidth(0.8f) }
//                ?: Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text.asString(),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            endValueContent?.invoke()
        }
    }
}
