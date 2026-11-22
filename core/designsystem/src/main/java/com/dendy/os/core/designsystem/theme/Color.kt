package com.dendy.os.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class DendyColorPalette(
    // Primary backgrounds (Light theme inspired by Apple / Rivian minimalism)
    val background: Color,
    val surface: Color,
    val surfaceGlass: Color,
    val surfaceHighlight: Color,
    val surfaceSubtle: Color,
    val border: Color,
    val borderLight: Color,

    // Typography & Content
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val iconPrimary: Color,
    val iconSecondary: Color,

    // Brand & Status Accents
    val accentPrimary: Color,
    val accentGlow: Color,
    val success: Color,
    val warning: Color,
    val danger: Color,
    val info: Color,

    // Vehicle Specific
    val climateCool: Color,
    val climateWarm: Color,
    val tirePressureNormal: Color,
    val tirePressureAlert: Color
)

// Premium Light Automotive Palette (Default)
val DendyLightColors = DendyColorPalette(
    background = Color(0xFFF3F5F9),          // Soft porcelain automotive grey-white
    surface = Color(0xFFFFFFFF),             // Pure white card base
    surfaceGlass = Color(0xCCFFFFFF),        // 80% opacity frosted glass
    surfaceHighlight = Color(0xFFF8FAFC),    // Ultra-light hover/active surface
    surfaceSubtle = Color(0xFFE2E8F0),       // Divider and neutral badge
    border = Color(0x26000000),              // 15% black hairline border
    borderLight = Color(0x14000000),         // 8% black border for subtle cards

    textPrimary = Color(0xFF0F172A),         // Deep Slate Black for highest glare readability
    textSecondary = Color(0xFF475569),       // Slate 600
    textTertiary = Color(0xFF94A3B8),        // Slate 400
    iconPrimary = Color(0xFF1E293B),         // Deep icon contrast
    iconSecondary = Color(0xFF64748B),       // Muted icon

    accentPrimary = Color(0xFF0284C7),       // Sky Blue (Electric & Crisp)
    accentGlow = Color(0x260284C7),          // 15% cyan glow
    success = Color(0xFF10B981),             // Emerald 500
    warning = Color(0xFFF59E0B),             // Amber 500
    danger = Color(0xFFEF4444),              // Rose 500
    info = Color(0xFF3B82F6),                // Blue 500

    climateCool = Color(0xFF0EA5E9),
    climateWarm = Color(0xFFF97316),
    tirePressureNormal = Color(0xFF10B981),
    tirePressureAlert = Color(0xFFEF4444)
)

// Automatic Night Automotive Palette (High-contrast, Anti-glare for driving in darkness)
val DendyDarkColors = DendyColorPalette(
    background = Color(0xFF0A0D14),
    surface = Color(0xFF121722),
    surfaceGlass = Color(0xCC18202F),
    surfaceHighlight = Color(0xFF1F293D),
    surfaceSubtle = Color(0xFF27354E),
    border = Color(0x2EFFFFFF),
    borderLight = Color(0x14FFFFFF),

    textPrimary = Color(0xFFF8FAFC),
    textSecondary = Color(0xFF94A3B8),
    textTertiary = Color(0xFF64748B),
    iconPrimary = Color(0xFFE2E8F0),
    iconSecondary = Color(0xFF94A3B8),

    accentPrimary = Color(0xFF38BDF8),
    accentGlow = Color(0x3338BDF8),
    success = Color(0xFF34D399),
    warning = Color(0xFFFBBF24),
    danger = Color(0xFFF87171),
    info = Color(0xFF60A5FA),

    climateCool = Color(0xFF38BDF8),
    climateWarm = Color(0xFFFB923C),
    tirePressureNormal = Color(0xFF34D399),
    tirePressureAlert = Color(0xFFF87171)
)

val LocalDendyColors = staticCompositionLocalOf { DendyLightColors }

