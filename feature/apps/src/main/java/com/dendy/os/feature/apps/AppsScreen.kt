package com.dendy.os.feature.apps

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.dendy.os.core.designsystem.theme.DendyShapes
import com.dendy.os.core.designsystem.theme.DendyTheme

data class AutomotiveAppItem(
    val id: String,
    val name: String,
    val icon: ImageVector,
    val packageName: String? = null
)

@Composable
fun AppsScreen(
    onLaunchApp: (AutomotiveAppItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val defaultApps = listOf(
        AutomotiveAppItem("1", "Яндекс Навигатор", Icons.Default.Map, "ru.yandex.yandexnavi"),
        AutomotiveAppItem("2", "Музыка", Icons.Default.MusicNote, null),
        AutomotiveAppItem("3", "Радио FM", Icons.Default.Radio, null),
        AutomotiveAppItem("4", "Телефон", Icons.Default.Phone, null),
        AutomotiveAppItem("5", "Камеры 360°", Icons.Default.Videocam, null),
        AutomotiveAppItem("6", "Omoda Бортсеть", Icons.Default.DirectionsCar, null),
        AutomotiveAppItem("7", "Видеорегистратор", Icons.Default.CameraAlt, null),
        AutomotiveAppItem("8", "Интернет Браузер", Icons.Default.Web, null),
        AutomotiveAppItem("9", "Настройки DENDY", Icons.Default.Settings, null)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DendyTheme.colors.background)
            .padding(24.dp)
    ) {
        Text(
            text = "Приложения DENDY OS",
            style = DendyTheme.typography.screenTitle,
            color = DendyTheme.colors.textPrimary
        )
        Spacer(modifier = Modifier.height(18.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 130.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(defaultApps) { app ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(DendyShapes.large)
                        .background(DendyTheme.colors.surfaceGlass)
                        .clickable { onLaunchApp(app) }
                        .padding(18.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(DendyTheme.colors.surfaceHighlight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = app.icon,
                            contentDescription = app.name,
                            tint = DendyTheme.colors.accentPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = app.name,
                        style = DendyTheme.typography.bodyMedium,
                        color = DendyTheme.colors.textPrimary
                    )
                }
            }
        }
    }
}
