package com.sugesw.ankerpods.data.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class AppMode(val label: String) {
    NORMAL("Normal"),
    SPOR("Spor"),
    SESSIZ("Sessiz")
}

data class AppSettings(
    val developerMode: Boolean = false,
    val mode: AppMode = AppMode.NORMAL,
    val duckMs: Int = 1500
)

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsStore(private val context: Context) {

    private object Keys {
        val DEV = booleanPreferencesKey("developer_mode")
        val MODE = stringPreferencesKey("mode")
        val DUCK = intPreferencesKey("duck_ms")
    }

    val flow: Flow<AppSettings> = context.dataStore.data.map { p ->
        AppSettings(
            developerMode = p[Keys.DEV] ?: false,
            mode = AppMode.values().firstOrNull { it.name == p[Keys.MODE] } ?: AppMode.NORMAL,
            duckMs = p[Keys.DUCK] ?: 1500
        )
    }

    suspend fun setDeveloperMode(value: Boolean) {
        context.dataStore.edit { it[Keys.DEV] = value }
    }

    suspend fun setMode(mode: AppMode) {
        context.dataStore.edit { it[Keys.MODE] = mode.name }
    }

    suspend fun setDuckMs(ms: Int) {
        context.dataStore.edit { it[Keys.DUCK] = ms }
    }
}
