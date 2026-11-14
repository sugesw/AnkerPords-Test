package com.sugesw.ankerpods

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sugesw.ankerpods.data.log.LogEntry
import com.sugesw.ankerpods.data.log.LogResult
import com.sugesw.ankerpods.data.log.LogSource
import com.sugesw.ankerpods.data.settings.AppMode
import com.sugesw.ankerpods.data.settings.AppSettings
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val a = app as AnkerPodsApp

    val logs: StateFlow<List<LogEntry>> = a.logRepo.observe()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val settings: StateFlow<AppSettings> = a.settings.flow
        .stateIn(viewModelScope, SharingStarted.Eagerly, AppSettings())

    init {
        viewModelScope.launch {
            a.logRepo.add(LogSource.SYSTEM, "Uygulama açıldı", LogResult.OK, "v${BuildConfig.VERSION_NAME}")
        }
    }

    fun setDeveloperMode(value: Boolean) {
        viewModelScope.launch { a.settings.setDeveloperMode(value) }
    }

    fun setMode(mode: AppMode) {
        viewModelScope.launch { a.settings.setMode(mode) }
    }

    fun setDuckMs(ms: Int) {
        viewModelScope.launch { a.settings.setDuckMs(ms) }
    }

    fun clearLogs() {
        viewModelScope.launch { a.logRepo.clear() }
    }

    fun addLog(source: LogSource, action: String, result: LogResult, detail: String = "") {
        viewModelScope.launch { a.logRepo.add(source, action, result, detail) }
    }
}
