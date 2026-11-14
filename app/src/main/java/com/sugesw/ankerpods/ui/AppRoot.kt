package com.sugesw.ankerpods.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sugesw.ankerpods.MainViewModel
import com.sugesw.ankerpods.data.log.LogResult
import com.sugesw.ankerpods.data.log.LogSource
import com.sugesw.ankerpods.ui.screens.AboutScreen
import com.sugesw.ankerpods.ui.screens.DevScreen
import com.sugesw.ankerpods.ui.screens.HomeScreen
import com.sugesw.ankerpods.ui.screens.LogScreen
import com.sugesw.ankerpods.ui.screens.SettingsScreen

private enum class Tab(val title: String, val icon: ImageVector) {
    HOME("Ana", Icons.Filled.Home),
    LOG("Log", Icons.AutoMirrored.Filled.List),
    SETTINGS("Ayarlar", Icons.Filled.Settings),
    DEV("Geliştirici", Icons.Filled.Build),
    ABOUT("Hakkında", Icons.Filled.Info)
}

@Composable
fun AppRoot(vm: MainViewModel = viewModel()) {
    val settings by vm.settings.collectAsState()
    val logs by vm.logs.collectAsState()

    val tabs = Tab.values().filter { it != Tab.DEV || settings.developerMode }
    var selected by rememberSaveable { mutableStateOf(Tab.HOME.name) }
    val current = tabs.firstOrNull { it.name == selected } ?: Tab.HOME

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                tabs.forEach { tab ->
                    NavigationBarItem(
                        selected = tab == current,
                        onClick = { selected = tab.name },
                        icon = { Icon(tab.icon, contentDescription = tab.title) },
                        label = { Text(tab.title, fontSize = 10.sp, maxLines = 1) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize()) {
            when (current) {
                Tab.HOME -> HomeScreen(settings, vm::setMode)
                Tab.LOG -> LogScreen(logs, vm::clearLogs)
                Tab.SETTINGS -> SettingsScreen(settings, vm::setDuckMs)
                Tab.DEV -> DevScreen { vm.addLog(LogSource.SYSTEM, "Deneme satırı", LogResult.OK, "Geliştirici modu testi") }
                Tab.ABOUT -> AboutScreen(settings.developerMode, vm::setDeveloperMode)
            }
        }
    }
}
