package com.dendy.os.feature.assistant

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dendy.os.core.ai.AssistantChatMessage
import com.dendy.os.core.ai.AssistantLanguage
import com.dendy.os.core.ai.AssistantStatus
import com.dendy.os.core.designsystem.components.DendyGlassCard
import com.dendy.os.core.designsystem.components.DendyIconButton
import com.dendy.os.core.designsystem.components.DendyStatusBadge
import com.dendy.os.core.designsystem.theme.DendyShapes
import com.dendy.os.core.designsystem.theme.DendyTheme

@Composable
fun DendyAssistantOverlay(
    isVisible: Boolean,
    status: AssistantStatus,
    currentLanguage: AssistantLanguage,
    messages: List<AssistantChatMessage>,
    onDismiss: () -> Unit,
    onVoiceTrigger: () -> Unit,
    onSendQuery: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0x99000000))
                .clickable(onClick = onDismiss),
            contentAlignment = Alignment.Center
        ) {
            DendyGlassCard(
                modifier = Modifier
                    .width(760.dp)
                    .height(480.dp)
                    .clickable(enabled = false) {}, // prevent click-through dismiss
                shape = DendyShapes.large,
                elevation = 12.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.linearGradient(
                                            listOf(Color(0xFF0284C7), Color(0xFF8B5CF6))
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Mic,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "DENDY AI",
                                    style = DendyTheme.typography.cardTitle,
                                    color = DendyTheme.colors.textPrimary
                                )
                                Text(
                                    text = "Голосовой помощник Omoda C5 • ${currentLanguage.displayName}",
                                    style = DendyTheme.typography.statusCaption,
                                    color = DendyTheme.colors.textSecondary
                                )
                            }
                        }

                        DendyIconButton(
                            icon = Icons.Default.Close,
                            contentDescription = "Закрыть",
                            onClick = onDismiss,
                            size = 44.dp
                        )
                    }

                    // Messages History
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(messages) { msg ->
                            val isUser = msg.isUser
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                            ) {
                                DendyGlassCard(
                                    backgroundColor = if (isUser) DendyTheme.colors.accentPrimary else DendyTheme.colors.surfaceHighlight,
                                    shape = DendyShapes.medium
                                ) {
                                    Text(
                                        text = msg.text,
                                        style = DendyTheme.typography.bodyLarge,
                                        color = if (isUser) Color.White else DendyTheme.colors.textPrimary,
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Fast Quick Command Chips (RU / KZ / EN)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        QuickChip("Включи климат 21°C") { onSendQuery("Установи климат на 21 градус") }
                        QuickChip("Давление в шинах") { onSendQuery("Какое давление в шинах?") }
                        QuickChip("Режим Спорт") { onSendQuery("Включи спорт режим") }
                        QuickChip("Ауа райы қандай?") { onSendQuery("Ауа райы қандай?") }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Input & Voice Action Row
                    var inputText by remember { mutableStateOf("") }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            placeholder = { Text("Скажите или введите команду...") },
                            modifier = Modifier.weight(1f),
                            shape = DendyShapes.medium,
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        DendyIconButton(
                            icon = Icons.Default.Mic,
                            contentDescription = "Слушать",
                            onClick = onVoiceTrigger,
                            isActive = status == AssistantStatus.LISTENING,
                            size = 54.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        DendyIconButton(
                            icon = Icons.Default.Send,
                            contentDescription = "Отправить",
                            onClick = {
                                if (inputText.isNotBlank()) {
                                    onSendQuery(inputText)
                                    inputText = ""
                                }
                            },
                            isActive = true,
                            size = 54.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickChip(text: String, onClick: () -> Unit) {
    DendyGlassCard(
        backgroundColor = DendyTheme.colors.surfaceHighlight,
        shape = DendyShapes.full,
        onClick = onClick
    ) {
        Text(
            text = text,
            style = DendyTheme.typography.statusCaption,
            color = DendyTheme.colors.accentPrimary,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}
