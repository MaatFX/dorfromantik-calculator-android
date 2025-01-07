package fr.mathieu.dorfromantikscorecalculateur.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = TextLight,
    secondary = Surface, // Use Surface as a secondary color
    onSecondary = TextDark,
    background = Surface, // Light teal as background
    onBackground = TextDark,
    surface = Surface,
    onSurface = TextDark
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary, // Deep teal as primary
    onPrimary = TextLight,
    secondary = Surface, // Use Surface as a secondary color
    onSecondary = TextDark,
    background = Color.Black, // Black background for dark theme
    onBackground = TextLight,
    surface = Color(0xFF37474F), // Darker surface for dark mode
    onSurface = TextLight
)


@Composable
fun DorfromantikScoreCalculateurTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}

