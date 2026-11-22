package com.dendy.os.feature.climate

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dendy.os.core.designsystem.components.DendyGlassCard
import com.dendy.os.core.designsystem.components.DendyIconButton
import com.dendy.os.core.designsystem.components.DendyStatusBadge
import com.dendy.os.core.designsystem.theme.DendyShapes
import com.dendy.os.core.designsystem.theme.DendyTheme
import com.dendy.os.core.vehicle.model.ClimateState

@Composable
fun ClimateScreen(
    climate: ClimateState,
    onAdjustDriverTemp: (Float) -> Unit,
    onAdjustPassengerTemp: (Float) -> Unit,
    onToggleAc: () -> Unit,
    onToggleRecirculation: () -> Unit,
    onAdjustFanSpeed: (Int) -> Unit,
    onSetSeatHeating: (Boolean, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DendyTheme.colors.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Двухзонный климат-контроль",
                    style = DendyTheme.typography.screenTitle,
                    color = DendyTheme.colors.textPrimary
                )
                Text(
                    text = "Omoda C5 AWD • Салонный фильтр очищен",
                    style = DendyTheme.typography.bodyMedium,
                    color = DendyTheme.colors.textSecondary
                )
            }

            DendyStatusBadge(
                text = if (climate.isAuto) "AUTO ON" else "MANUAL",
                badgeColor = if (climate.isAuto) DendyTheme.colors.accentPrimary else DendyTheme.colors.surfaceSubtle,
                textColor = if (climate.isAuto) Color.White else DendyTheme.colors.textSecondary
            )
        }

        // Center Dual Temperature Zone Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Driver Zone
            TemperatureZoneCard(
                title = "Водитель",
                temperature = climate.driverTempCelsius,
                seatHeating = climate.driverSeatHeatingLevel,
                onIncrease = { onAdjustDriverTemp(climate.driverTempCelsius + 0.5f) },
                onDecrease = { onAdjustDriverTemp(climate.driverTempCelsius - 0.5f) },
                onCycleSeatHeating = { onSetSeatHeating(true, (climate.driverSeatHeatingLevel + 1) % 4) },
                modifier = Modifier.weight(1f)
            )

            // Passenger Zone
            TemperatureZoneCard(
                title = "Пассажир",
                temperature = climate.passengerTempCelsius,
                seatHeating = climate.passengerSeatHeatingLevel,
                onIncrease = { onAdjustPassengerTemp(climate.passengerTempCelsius + 0.5f) },
                onDecrease = { onAdjustPassengerTemp(climate.passengerTempCelsius - 0.5f) },
                onCycleSeatHeating = { onSetSeatHeating(false, (climate.passengerSeatHeatingLevel + 1) % 4) },
                modifier = Modifier.weight(1f)
            )
        }

        // Bottom Climate Quick Bar (A/C, Fan Speed, Recirculation)
        DendyGlassCard(
            modifier = Modifier.fillMaxWidth(),
            shape = DendyShapes.large
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // A/C Toggle
                DendyIconButton(
                    icon = Icons.Default.AcUnit,
                    contentDescription = "A/C",
                    onClick = onToggleAc,
                    isActive = climate.isAcOn,
                    size = 58.dp
                )

                // Fan Speed 1-7
                Row(verticalAlignment = Alignment.CenterVertically) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.Air,
                        contentDescription = null,
                        tint = DendyTheme.colors.iconPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Вентилятор: ${climate.fanSpeed}",
                        style = DendyTheme.typography.cardTitle,
                        color = DendyTheme.colors.textPrimary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    DendyIconButton(
                        icon = Icons.Default.Remove,
                        contentDescription = "Fan -",
                        onClick = { onAdjustFanSpeed((climate.fanSpeed - 1).coerceAtLeast(1)) },
                        size = 46.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    DendyIconButton(
                        icon = Icons.Default.Add,
                        contentDescription = "Fan +",
                        onClick = { onAdjustFanSpeed((climate.fanSpeed + 1).coerceAtMost(7)) },
                        size = 46.dp
                    )
                }

                // Recirculation Toggle
                DendyIconButton(
                    icon = Icons.Default.Sync,
                    contentDescription = "Рециркуляция",
                    onClick = onToggleRecirculation,
                    isActive = climate.isRecirculationOn,
                    size = 58.dp
                )
            }
        }
    }
}

@Composable
private fun TemperatureZoneCard(
    title: String,
    temperature: Float,
    seatHeating: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onCycleSeatHeating: () -> Unit,
    modifier: Modifier = Modifier
) {
    DendyGlassCard(
        modifier = modifier,
        shape = DendyShapes.large
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = DendyTheme.typography.bodyLarge,
                color = DendyTheme.colors.textSecondary
            )
            Spacer(modifier = Modifier.height(14.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                DendyIconButton(
                    icon = Icons.Default.Remove,
                    contentDescription = "Минус",
                    onClick = onDecrease,
                    size = 54.dp
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = String.format("%.1f°", temperature),
                    style = DendyTheme.typography.clockHero.copy(fontSize = 52.sp),
                    color = DendyTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.width(24.dp))
                DendyIconButton(
                    icon = Icons.Default.Add,
                    contentDescription = "Плюс",
                    onClick = onIncrease,
                    size = 54.dp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Heated Seat Level Pill
            DendyGlassCard(
                modifier = Modifier,
                backgroundColor = if (seatHeating > 0) DendyTheme.colors.climateWarm.copy(alpha = 0.2f) else DendyTheme.colors.surfaceHighlight,
                shape = DendyShapes.full,
                onClick = onCycleSeatHeating
            ) {
                Text(
                    text = "Подогрев сиденья: " + if (seatHeating == 0) "Выкл" else "Уровень $seatHeating/3",
                    style = DendyTheme.typography.labelBold,
                    color = if (seatHeating > 0) DendyTheme.colors.climateWarm else DendyTheme.colors.textSecondary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

