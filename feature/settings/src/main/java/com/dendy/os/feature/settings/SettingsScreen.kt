package com.dendy.os.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.dendy.os.core.ai.AssistantLanguage
import com.dendy.os.core.designsystem.components.DendyGlassCard
import com.dendy.os.core.designsystem.theme.DendyShapes
import com.dendy.os.core.designsystem.theme.DendyTheme

@Composable
fun SettingsScreen(
    isDarkTheme: Boolean,
    onToggleDarkTheme: (Boolean) -> Unit,
    currentLanguage: AssistantLanguage,
    onSelectLanguage: (AssistantLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(DendyTheme.colors.background)
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Left Column: Interface & Launcher Settings
        DendyGlassCard(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            shape = DendyShapes.large
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Настройки интерфейса DENDY",
                    style = DendyTheme.typography.cardTitle,
                    color = DendyTheme.colors.textPrimary
                )

                // Theme Switcher (Default is Light Automotive)
                SettingsRowWithSwitch(
                    icon = Icons.Default.BrightnessMedium,
                    title = "Ночной режим (Темная тема)",
                    subtitle = "Автоматическое переключение по датчику света фар",
                    isChecked = isDarkTheme,
                    onCheckedChange = onToggleDarkTheme
                )

                // Launcher Home replacement
                SettingsRowWithSwitch(
                    icon = Icons.Default.Home,
                    title = "Запуск по умолчанию (Home)",
                    subtitle = "DENDY OS заменяет штатный домашний экран",
                    isChecked = true,
                    onCheckedChange = { /* Launcher preference */ }
                )

                // Language Selector
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = null,
                            tint = DendyTheme.colors.iconPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Язык системы и DENDY AI",
                            style = DendyTheme.typography.bodyLarge,
                            color = DendyTheme.colors.textPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        LanguageChip(
                            label = "Русский",
                            isSelected = currentLanguage == AssistantLanguage.RUSSIAN
                        ) { onSelectLanguage(AssistantLanguage.RUSSIAN) }

                        LanguageChip(
                            label = "Қазақша",
                            isSelected = currentLanguage == AssistantLanguage.KAZAKH
                        ) { onSelectLanguage(AssistantLanguage.KAZAKH) }

                        LanguageChip(
                            label = "English",
                            isSelected = currentLanguage == AssistantLanguage.ENGLISH
                        ) { onSelectLanguage(AssistantLanguage.ENGLISH) }
                    }
                }
            }
        }

        // Right Column: System info & Omoda Diagnostics
        DendyGlassCard(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            shape = DendyShapes.large
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "О системе DENDY OS",
                    style = DendyTheme.typography.cardTitle,
                    color = DendyTheme.colors.textPrimary
                )

                InfoRow("Версия сборки", "DENDY OS 1.0.0-OMODA (Release)")
                InfoRow("Совместимость", "Omoda C5 (2023) 1.6 Turbo AWD")
                InfoRow("Режим работы", "Безопасный Android Launcher (Sandboxed)")
                InfoRow("Интеграция CAN", "Чтение безопасной телеметрии (Non-invasive)")
                InfoRow("AI Ядро", "DENDY Assistant Multi-Intent Engine (RU/KZ/EN)")
                InfoRow("Разработчик", "DENDY Automotive Team")

                Spacer(modifier = Modifier.weight(1f))

                DendyGlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = DendyTheme.colors.surfaceHighlight,
                    shape = DendyShapes.medium
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = DendyTheme.colors.accentPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "DENDY OS не изменяет заводскую прошивку и не влияет на гарантию автомобиля.",
                            style = DendyTheme.typography.statusCaption,
                            color = DendyTheme.colors.textSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsRowWithSwitch(
    icon: ImageVector,
    title: String,
    subtitle: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = DendyTheme.colors.iconPrimary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = DendyTheme.typography.bodyLarge,
                    color = DendyTheme.colors.textPrimary
                )
                Text(
                    text = subtitle,
                    style = DendyTheme.typography.statusCaption,
                    color = DendyTheme.colors.textSecondary
                )
            }
        }
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
private fun LanguageChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    DendyGlassCard(
        backgroundColor = if (isSelected) DendyTheme.colors.accentPrimary else DendyTheme.colors.surfaceHighlight,
        shape = DendyShapes.full,
        onClick = onClick
    ) {
        Text(
            text = label,
            style = DendyTheme.typography.labelBold,
            color = if (isSelected) androidx.compose.ui.graphics.Color.White else DendyTheme.colors.textPrimary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = DendyTheme.typography.bodyMedium,
            color = DendyTheme.colors.textSecondary
        )
        Text(
            text = value,
            style = DendyTheme.typography.labelBold,
            color = DendyTheme.colors.textPrimary
        )
    }
}
