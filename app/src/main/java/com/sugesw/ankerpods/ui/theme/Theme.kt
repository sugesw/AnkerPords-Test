package com.sugesw.ankerpods.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val NeonBg = Color(0xFF0A0E14)
val NeonSurface = Color(0xFF111823)
val NeonCyan = Color(0xFF00F5D4)
val NeonMagenta = Color(0xFFFF2E97)
val NeonYellow = Color(0xFFFFE600)

private val Scheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = Color.Black,
    secondary = NeonMagenta,
    onSecondary = Color.Black,
    tertiary = NeonYellow,
    onTertiary = Color.Black,
    background = NeonBg,
    onBackground = Color(0xFFD6F5F0),
    surface = NeonSurface,
    onSurface = Color(0xFFD6F5F0),
    surfaceVariant = Color(0xFF1A2432),
    onSurfaceVariant = Color(0xFF8FA5B2),
    outline = Color(0xFF2B3A4A),
    error = Color(0xFFFF5370)
)

private val Mono = FontFamily.Monospace

private val AppTypography = Typography(
    headlineSmall = TextStyle(fontFamily = Mono, fontWeight = FontWeight.Bold, fontSize = 22.sp),
    titleMedium = TextStyle(fontFamily = Mono, fontWeight = FontWeight.SemiBold, fontSize = 16.sp),
    bodyLarge = TextStyle(fontFamily = Mono, fontSize = 14.sp),
    bodyMedium = TextStyle(fontFamily = Mono, fontSize = 13.sp),
    bodySmall = TextStyle(fontFamily = Mono, fontSize = 11.sp),
    labelLarge = TextStyle(fontFamily = Mono, fontWeight = FontWeight.Medium, fontSize = 13.sp),
    labelMedium = TextStyle(fontFamily = Mono, fontWeight = FontWeight.Bold, fontSize = 12.sp)
)

@Composable
fun AnkerPodsTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Scheme, typography = AppTypography, content = content)
}
