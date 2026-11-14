package com.sugesw.ankerpods.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.sugesw.ankerpods.data.settings.AppSettings
import com.sugesw.ankerpods.ui.NeonCard
import com.sugesw.ankerpods.ui.SectionTitle
import kotlin.math.roundToInt

private val permissions = listOf(
    "Bildirim erişimi" to "Aşama 2 · bildirim okuma",
    "Telefon durumu / arama cevaplama" to "Aşama 3 · arama desteği",
    "Rehber" to "Aşama 3 · kişi adı ve öncelikli kişiler",
    "Rahatsız Etme erişimi" to "Aşama 3 · Sustur özelliği",
    "Bluetooth bağlantısı" to "Aşama 3 · cihaz eşleşmesi ve bağlantı olayları",
    "Konum (arka plan dahil)" to "Aşama 6 · son konum kaydı",
    "Sistem ayarlarını değiştirme" to "Aşama 3 · bağlantıya göre zil sesi",
    "Pil kısıtlaması / otomatik başlatma (HyperOS)" to "Aşama 2 · sürekli çalışan servis"
)

@Composable
fun SettingsScreen(settings: AppSettings, onDuckMs: (Int) -> Unit) {
    var tmp by remember(settings.duckMs) { mutableFloatStateOf(settings.duckMs.toFloat()) }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            NeonCard {
                SectionTitle("MÜZİK KISMA HIZI")
                Text(
                    "Kısma / geri dönme süresi: ${"%.1f".format(tmp / 1000f)} sn",
                    style = MaterialTheme.typography.bodyMedium
                )
                Slider(
                    value = tmp,
                    onValueChange = { tmp = (it / 100f).roundToInt() * 100f },
                    onValueChangeFinished = { onDuckMs(tmp.roundToInt()) },
                    valueRange = 500f..3000f
                )
                Text(
                    "Kaydedilir; ducking aşama 3'te bağlanacak.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        item {
            NeonCard(accent = MaterialTheme.colorScheme.tertiary) {
                SectionTitle("İZİNLER (ilgili aşamada istenecek)", MaterialTheme.colorScheme.tertiary)
                permissions.forEach { (name, why) ->
                    Text(name, style = MaterialTheme.typography.bodyMedium)
                    Text(
                        "  └ $why",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
