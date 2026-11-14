package com.sugesw.ankerpods.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.sugesw.ankerpods.data.log.LogEntry
import com.sugesw.ankerpods.data.log.LogResult
import com.sugesw.ankerpods.data.log.LogRepository
import com.sugesw.ankerpods.data.log.LogSource
import com.sugesw.ankerpods.ui.NeonCard
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private fun sourceLabel(name: String) = LogSource.values().firstOrNull { it.name == name }?.label ?: name
private fun resultLabel(name: String) = LogResult.values().firstOrNull { it.name == name }?.label ?: name

@Composable
private fun resultColor(name: String): Color = when (name) {
    "OK" -> MaterialTheme.colorScheme.primary
    "FAIL" -> MaterialTheme.colorScheme.error
    "NO_REPLY" -> MaterialTheme.colorScheme.tertiary
    "PENDING" -> MaterialTheme.colorScheme.secondary
    else -> MaterialTheme.colorScheme.onSurfaceVariant
}

@Composable
fun LogScreen(logs: List<LogEntry>, onClear: () -> Unit) {
    var filter by remember { mutableStateOf<LogSource?>(null) }
    val shown = remember(logs, filter) {
        val f = filter
        if (f == null) logs else logs.filter { it.source == f.name }
    }
    val clipboard = LocalClipboardManager.current
    val fmt = remember { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }

    Column(Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 12.dp)) {
        Text(
            "LOG  (${shown.size} / son ${LogRepository.MAX_ROWS})",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Row(
            Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(selected = filter == null, onClick = { filter = null }, label = { Text("Hepsi") })
            LogSource.values().forEach { s ->
                FilterChip(selected = filter == s, onClick = { filter = s }, label = { Text(s.label) })
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                val text = shown.joinToString("\n") {
                    "${fmt.format(Date(it.time))} [${sourceLabel(it.source)}] ${it.action} -> ${resultLabel(it.result)} ${it.detail}".trim()
                }
                clipboard.setText(AnnotatedString(text))
            }) { Text("Kopyala") }
            OutlinedButton(onClick = onClear) { Text("Temizle") }
        }

        if (shown.isEmpty()) {
            Text(
                "Kayıt yok.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(top = 10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(shown, key = { it.id }) { e ->
                    NeonCard(accent = resultColor(e.result)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(fmt.format(Date(e.time)), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(sourceLabel(e.source), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
                            Text(resultLabel(e.result), style = MaterialTheme.typography.labelMedium, color = resultColor(e.result))
                        }
                        Text(e.action, style = MaterialTheme.typography.bodyMedium)
                        if (e.detail.isNotBlank()) {
                            Text(e.detail, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }
}
