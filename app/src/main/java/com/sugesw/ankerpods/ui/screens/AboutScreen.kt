package com.sugesw.ankerpods.ui.screens

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sugesw.ankerpods.BuildConfig
import com.sugesw.ankerpods.Branding
import com.sugesw.ankerpods.ui.NeonCard
import com.sugesw.ankerpods.ui.SectionTitle

@Composable
fun AboutScreen(developerMode: Boolean, onToggleDeveloper: (Boolean) -> Unit) {
    val context = LocalContext.current
    var taps by remember { mutableIntStateOf(0) }

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(Branding.APP_NAME, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)

        NeonCard {
            SectionTitle("SÜRÜM")
            Text(
                "v${BuildConfig.VERSION_NAME}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.clickable {
                    taps += 1
                    if (taps >= 7) {
                        taps = 0
                        val next = !developerMode
                        onToggleDeveloper(next)
                        Toast.makeText(
                            context,
                            if (next) "Geliştirici modu açıldı" else "Geliştirici modu kapandı",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }

        NeonCard(accent = MaterialTheme.colorScheme.secondary) {
            SectionTitle("GELİŞTİRİCİ", MaterialTheme.colorScheme.secondary)
            Text(Branding.DEVELOPER, style = MaterialTheme.typography.titleMedium)
            Button(
                onClick = {
                    try {
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Branding.LINK_URL)))
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(context, "Bağlantı açılamadı", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) { Text(Branding.LINK_LABEL) }
        }

        Text(
            "Soundcore ve Anker, ilgili sahiplerinin ticari markalarıdır. Bu uygulama resmî değildir, kişisel kullanım içindir.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
