package com.shifthackz.joyreactor.presentation.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shifthackz.joyreactor.presentation.R

@Composable
fun JoyReactorComposable(
    modifier: Modifier = Modifier,
) {
    Row(modifier) {
        val dark = isSystemInDarkTheme()
        Image(
            modifier = Modifier
                .width(36.dp)
                .height(32.dp),
            painter = painterResource(id = R.drawable.ic_reactor),
            contentDescription = null,
            colorFilter = dark.takeIf { !it }?.let {
                ColorFilter.tint(Color.Black, BlendMode.SrcIn)
            },
        )
        Text(
            text = "JoyReactor",
            fontWeight = FontWeight.W700,
            fontSize = 20.sp,
        )
    }
}
