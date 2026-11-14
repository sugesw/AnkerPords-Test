package com.sugesw.ankerpods.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sugesw.ankerpods.Branding
import com.sugesw.ankerpods.data.settings.AppMode
import com.sugesw.ankerpods.data.settings.AppSettings
import com.sugesw.ankerpods.ui.NeonCard
import com.sugesw.ankerpods.ui.SectionTitle

private val roadmap = listOf(
    "Bildirim okuma" to "Aşama 2",
    "Arama anonsu / öncelikli kişiler" to "Aşama 3",
    "Filtreler ve modlar" to "Aşama 4",
    "Dinleme istatistiği" to "Aşama 5",
    "Son konum kaydı" to "Aşama 6",
    "ANC / EQ / sol-sağ pil (kulaklık)" to "Kulaklık + snoop log bekliyor"
)

@Composable
fun HomeScreen(settings: AppSettings, onMode: (AppMode) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column {
                Text(Branding.APP_NAME, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
                Text("> kulaklık yardımcısı_", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        item {
            NeonCard {
                SectionTitle("DURUM")
                Text("Kulaklık: bağlı değil", style = MaterialTheme.typography.bodyLarge)
                Text(
                    "Bluetooth katmanı henüz yok (doğrulanmamış / kulaklık bekleniyor).",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text("Pil: —", style = MaterialTheme.typography.bodyMedium)
            }
        }
        item {
            NeonCard(accent = MaterialTheme.colorScheme.secondary) {
                SectionTitle("MOD", MaterialTheme.colorScheme.secondary)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AppMode.values().forEach { m ->
                        FilterChip(
                            selected = settings.mode == m,
                            onClick = { onMode(m) },
                            label = { Text(m.label) }
                        )
                    }
                }
                Text(
                    "Mod seçimi kaydedilir; davranışı ilgili aşamalarda bağlanacak.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                )
            }
        }
        item {
            NeonCard(accent = MaterialTheme.colorScheme.tertiary) {
                SectionTitle("YOL HARİTASI", MaterialTheme.colorScheme.tertiary)
                roadmap.forEach { (name, state) ->
                    Text(name, style = MaterialTheme.typography.bodyMedium)
                    Text(
                        "  └ $state",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
