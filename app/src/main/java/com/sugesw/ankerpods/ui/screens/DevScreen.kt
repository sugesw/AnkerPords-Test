package com.sugesw.ankerpods.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sugesw.ankerpods.ui.NeonCard
import com.sugesw.ankerpods.ui.SectionTitle
import com.sugesw.ankerpods.util.Hex

@Composable
fun DevScreen(onTestLog: () -> Unit) {
    var hex by remember { mutableStateOf("") }
    val parsed = remember(hex) { Hex.parse(hex) }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            NeonCard(accent = MaterialTheme.colorScheme.secondary) {
                SectionTitle("HAM KOMUT (HEX)", MaterialTheme.colorScheme.secondary)
                OutlinedTextField(
                    value = hex,
                    onValueChange = { hex = it },
                    label = { Text("örn. 01 02 03") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium
                )
                Text(
                    when {
                        hex.isBlank() -> "Bayt yok."
                        parsed == null -> "Geçersiz hex."
                        else -> "${parsed.size} bayt: ${Hex.format(parsed)}"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Button(onClick = {}, enabled = false, modifier = Modifier.fillMaxWidth()) {
                    Text("Gönder (Bluetooth katmanı yok)")
                }
                Text(
                    "Gönderme ve ham cevap, kulaklık + snoop log sonrası bağlanacak.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        item {
            NeonCard {
                SectionTitle("TEST DÜĞMELERİ")
                OutlinedButton(onClick = onTestLog, modifier = Modifier.fillMaxWidth()) {
                    Text("Log'a deneme satırı yaz")
                }
                Text(
                    "Sahte anons, sahte bildirim, ducking ve zil testleri ilgili aşamalarda eklenecek.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
