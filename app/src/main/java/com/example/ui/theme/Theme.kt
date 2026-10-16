package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val InfernalColorScheme = darkColorScheme(
  primary = HellRed,
  onPrimary = Color.White,
  primaryContainer = HellRedDark,
  onPrimaryContainer = Color.White,
  secondary = HellOrange,
  onSecondary = Color.Black,
  secondaryContainer = DarkSurfaceElevated,
  onSecondaryContainer = HellGold,
  tertiary = AngelGold,
  onTertiary = Color.Black,
  background = DarkObsidian,
  onBackground = TextPrimary,
  surface = DarkVelvet,
  onSurface = TextPrimary,
  surfaceVariant = DarkSurfaceCard,
  onSurfaceVariant = TextSecondary,
  outline = DarkBorder
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  // Always use the immersive infernal dark theme as requested
  MaterialTheme(
    colorScheme = InfernalColorScheme,
    typography = Typography,
    content = content
  )
}
