package com.dendy.os.feature.vehicle

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
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dendy.os.core.designsystem.components.DendyGlassCard
import com.dendy.os.core.designsystem.components.DendyStatusBadge
import com.dendy.os.core.designsystem.theme.DendyShapes
import com.dendy.os.core.designsystem.theme.DendyTheme
import com.dendy.os.core.vehicle.model.DriveMode
import com.dendy.os.core.vehicle.model.TirePressure
import com.dendy.os.core.vehicle.model.VehicleState

@Composable
fun VehicleScreen(
    vehicleState: VehicleState,
    onSelectDriveMode: (DriveMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(DendyTheme.colors.background)
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Left Column: TPMS 4-Tire Diagram & Drive Modes
        DendyGlassCard(
            modifier = Modifier
                .weight(1.1f)
                .fillMaxHeight(),
            shape = DendyShapes.large
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Система контроля TPMS",
                        style = DendyTheme.typography.cardTitle,
                        color = DendyTheme.colors.textPrimary
                    )
                    DendyStatusBadge(
                        text = "Все шины в норме",
                        textColor = DendyTheme.colors.success
                    )
                }

                // 4 Tires Grid Layout
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TireBadge("Переднее Левое", vehicleState.frontLeftTire)
                    TireBadge("Переднее Правое", vehicleState.frontRightTire)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TireBadge("Заднее Левое", vehicleState.rearLeftTire)
                    TireBadge("Заднее Правое", vehicleState.rearRightTire)
                }

                // Drive Mode Selector
                Column {
                    Text(
                        text = "Режим трансмиссии AWD",
                        style = DendyTheme.typography.labelBold,
                        color = DendyTheme.colors.textSecondary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        DriveModeChip("ECO", vehicleState.driveMode == DriveMode.ECO) {
                            onSelectDriveMode(DriveMode.ECO)
                        }
                        DriveModeChip("NORMAL", vehicleState.driveMode == DriveMode.NORMAL) {
                            onSelectDriveMode(DriveMode.NORMAL)
                        }
                        DriveModeChip("SPORT", vehicleState.driveMode == DriveMode.SPORT) {
                            onSelectDriveMode(DriveMode.SPORT)
                        }
                    }
                }
            }
        }

        // Right Column: Telemetry & Safe Hardware Architecture Info
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
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Бортовая телеметрия",
                    style = DendyTheme.typography.cardTitle,
                    color = DendyTheme.colors.textPrimary
                )

                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    TelemetryRow("Скорость автомобиля", "${vehicleState.speedKmh} км/ч", Icons.Default.Speed)
                    TelemetryRow("Обороты двигателя", "${vehicleState.engineRpm} об/мин", Icons.Default.AllInclusive)
                    TelemetryRow("Напряжение АКБ", "${vehicleState.batteryVoltage} V", Icons.Default.BatteryChargingFull)
                    TelemetryRow("Температура снаружи", "+${vehicleState.outsideTempCelsius}°C", Icons.Default.Thermostat)
                    TelemetryRow("Пробег (Одометр)", "${vehicleState.odometerKm} км", Icons.Default.Speed)
                }

                // Hardware Info Box (Verification requirement from prompt)
                DendyGlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = DendyTheme.colors.surfaceHighlight,
                    shape = DendyShapes.medium
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Default.Memory,
                                contentDescription = null,
                                tint = DendyTheme.colors.accentPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Аппаратное ГУ Omoda C5",
                                style = DendyTheme.typography.labelBold,
                                color = DendyTheme.colors.textPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        val hw = vehicleState.hardwareInfo
                        Text(
                            text = "ОС: ${hw.androidVersion} | Дисплей: ${hw.displayResolution}",
                            style = DendyTheme.typography.statusCaption,
                            color = DendyTheme.colors.textSecondary
                        )
                        Text(
                            text = "Мост: ${hw.vendorBridgeType}",
                            style = DendyTheme.typography.statusCaption,
                            color = DendyTheme.colors.accentPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TireBadge(title: String, tire: TirePressure) {
    DendyGlassCard(
        modifier = Modifier.width(170.dp),
        backgroundColor = DendyTheme.colors.surfaceHighlight,
        shape = DendyShapes.medium
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                style = DendyTheme.typography.statusCaption,
                color = DendyTheme.colors.textSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "${tire.pressureBar}",
                    style = DendyTheme.typography.screenTitle.copy(fontSize = 24.sp),
                    color = if (tire.isAlert) DendyTheme.colors.danger else DendyTheme.colors.success
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "bar",
                    style = DendyTheme.typography.statusCaption,
                    color = DendyTheme.colors.textSecondary
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "+${tire.temperatureCelsius}°C",
                    style = DendyTheme.typography.statusCaption,
                    color = DendyTheme.colors.textTertiary
                )
            }
        }
    }
}

@Composable
private fun TelemetryRow(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material3.Icon(
                imageVector = icon,
                contentDescription = null,
                tint = DendyTheme.colors.iconSecondary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = DendyTheme.typography.bodyMedium,
                color = DendyTheme.colors.textSecondary
            )
        }
        Text(
            text = value,
            style = DendyTheme.typography.cardTitle.copy(fontSize = 16.sp),
            color = DendyTheme.colors.textPrimary
        )
    }
}

@Composable
private fun DriveModeChip(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    DendyGlassCard(
        modifier = Modifier,
        backgroundColor = if (isSelected) DendyTheme.colors.accentPrimary else DendyTheme.colors.surfaceHighlight,
        shape = DendyShapes.full,
        onClick = onClick
    ) {
        Text(
            text = name,
            style = DendyTheme.typography.labelBold,
            color = if (isSelected) Color.White else DendyTheme.colors.textPrimary,
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 10.dp)
        )
    }
}
