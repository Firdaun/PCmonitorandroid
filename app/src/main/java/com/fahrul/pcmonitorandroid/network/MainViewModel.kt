package com.fahrul.pcmonitorandroid.network

import androidx.lifecycle.ViewModel
import com.fahrul.pcmonitorandroid.network.blueprint.SystemStatsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _stats = MutableStateFlow<SystemStatsData?>(null)
    val stats: StateFlow<SystemStatsData?> = _stats.asStateFlow()

    private val _connectionStatus = MutableStateFlow("Terputus")
    val connectionStatus: StateFlow<String> = _connectionStatus.asStateFlow()

    private var webSocketClient: PCWebSocketClient? = null

    fun connectToPc(ipAddress: String){
        _connectionStatus.value = "Menyambungkan..."

        webSocketClient = PCWebSocketClient(
            ipAddress = ipAddress,
            onStatsReceived = { newData ->
                _stats.value = newData
                _connectionStatus.value = "Tersambung"
            },
            onConnectionClose = {
                _connectionStatus.value = "Terputus"
                _stats.value = null
            }
        )

        webSocketClient?.connect()
    }

    fun disconnect() {
        webSocketClient?.disconnect()
        _connectionStatus.value = "Terputus"
        _stats.value = null
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }
}