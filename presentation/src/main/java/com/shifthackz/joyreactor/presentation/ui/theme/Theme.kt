package com.shifthackz.joyreactor.presentation.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.graphics.luminance
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Orange,
    secondary = OrangeSecondary,
    tertiary = OrangeSecondary50,
    tertiaryContainer = OrangeSecondary50,
)

private val LightColorScheme = lightColorScheme(
    primary = Orange,
    secondary = OrangeSecondary,
    tertiary = OrangeSecondary,
    tertiaryContainer = OrangeSecondary50,


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun JoyYouTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    SetStatusBarColor(colorScheme.background)
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun SetStatusBarColor(color: Color = MaterialTheme.colorScheme.background) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val statusBarColor = color.toArgb()
            val appearanceLightStatusBar = statusBarColor.luminance > 0.5f
            window.statusBarColor = statusBarColor
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = appearanceLightStatusBar
        }
    }
}

@Composable
fun colors(light: Color, dark: Color): Color {
    if (isSystemInDarkTheme()) return dark
    return light
}
