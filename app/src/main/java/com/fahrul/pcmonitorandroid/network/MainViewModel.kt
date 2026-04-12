package com.fahrul.pcmonitorandroid.network

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahrul.pcmonitorandroid.network.blueprint.SystemStatsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class MainViewModel : ViewModel() {
    private val _stats = MutableStateFlow<SystemStatsData?>(null)
    val stats = _stats.asStateFlow()

    private val _connectionStatus = MutableStateFlow("Terputus")
    val connectionStatus = _connectionStatus.asStateFlow()

    private var webSocketClient: PCWebSocketClient? = null

    private var currentIp: String = ""
    private var currentSecretKey: String = ""

    private val httpClient = OkHttpClient()

    fun connectToPc(ipAddress: String, secretKey: String){
        currentIp = ipAddress
        currentSecretKey = secretKey
        _connectionStatus.value = "Menyambungkan..."

        webSocketClient = PCWebSocketClient(
            ipAddress = ipAddress,
            secretKey = secretKey,
            onStatsReceived = { newData ->
                _stats.value = newData
                _connectionStatus.value = "Tersambung"
            },
            onConnectionClose = { reason ->
                _connectionStatus.value = reason
                _stats.value = null
            }
        )

        webSocketClient?.connect()
    }

    fun sendCommand(action: String) {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val client = httpClient
                val json = """{"action": "$action"}"""
                val body = json.toRequestBody("application/json".toMediaTypeOrNull())

                val request = Request.Builder()
                    .url("http://$currentIp:8000/api/command/action")
                    .addHeader("x-api-key", currentSecretKey)
                    .post(body)
                    .build()

                client.newCall(request).execute().use{response ->
                    if (response.isSuccessful){
                        Log.d("NEMBAK", "Berhasil: ${response.body.string()}")
                    }else {
                        Log.d("NEMBAK", "Gagal (Ditolak Server): ${response.code}")
                    }
                }
            }catch (e: Exception){
                Log.e("NEMBAK", "Server Mati / Error: ${e.message}")
            }
        }
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