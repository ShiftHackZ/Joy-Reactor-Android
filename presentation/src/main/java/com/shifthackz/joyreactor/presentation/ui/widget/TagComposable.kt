package com.shifthackz.joyreactor.presentation.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shifthackz.joyreactor.entity.Tag

private val imageSize = 40.dp
private val offset = imageSize + 6.dp

@Composable
fun TagComposable(
    modifier: Modifier = Modifier,
    tag: Tag,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(4.dp)
    Box(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxSize()
            .height(imageSize + 8.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer, shape)
            .clip(shape)
            .clickable { onClick() },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Box (
                modifier = Modifier.padding(top = 4.dp, start = 4.dp),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(imageSize)
                        .clip(shape),
                    model = tag.imageUrl,
                    contentDescription = null,
                )
            }
            Text(
                modifier = Modifier.padding(start = offset - imageSize),
                text = tag.name,
                fontWeight = FontWeight.W600,
                fontSize = 18.sp,
            )
        }
    }
}
