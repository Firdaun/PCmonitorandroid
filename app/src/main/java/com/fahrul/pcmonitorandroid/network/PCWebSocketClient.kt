package com.fahrul.pcmonitorandroid.network

import android.util.Log
import com.fahrul.pcmonitorandroid.network.blueprint.SystemStatsData
import com.fahrul.pcmonitorandroid.network.blueprint.SystemStatsResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class PCWebSocketClient(
    private val ipAddress: String,
    private val secretKey: String,
    private val onStatsReceived: (SystemStatsData) -> Unit,
    private val onConnectionClose: (String) -> Unit,
) {
    private var webSocket: WebSocket? = null
    private val client = OkHttpClient()
    private val gson = Gson()

    fun connect() {
        if (ipAddress.isBlank()) {
            Log.e("WS_CLIENT", "IP Address tidak boleh kosong")
            onConnectionClose("Filed kosong")
            return
        }
        val baseUrl = "ws://$ipAddress:8000"
        try {
            val request = Request.Builder()
                .url(baseUrl)
                .addHeader("x-api-key", secretKey)
                .build()
            webSocket = client.newWebSocket(request, object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    Log.d("WS_CLIENT", "Berhasil terhubung ke $baseUrl")
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    try {
                        val statsResponse = gson.fromJson(text, SystemStatsResponse::class.java)
                        if (statsResponse.type == "system_stats" && statsResponse.data != null) {
                            onStatsReceived(statsResponse.data)
                        }
                    } catch (e: Exception) {
                        Log.e("WS_CLIENT", "Gagal membaca JSON: ${e.message}")
                    }
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    Log.d("WS_CLIENT", "Koneksi WebSocket ditutup $reason")
                    if (code == 1008){
                        onConnectionClose("Kunci Salah")
                    }
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    Log.e("WS_CLIENT", "🔴 KONEKSI GAGAL KARENA: ${t.message}")
                    onConnectionClose("Terputus")
                }

                override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                    Log.d("WS_CLIENT", "Server memutus koneksi: $reason")

                    webSocket.close(1000, null)

                    if (code == 1008) {
                        onConnectionClose("Kunci Salah")
                    }
                }
            })

        } catch (e: Exception){
            Log.e("WS_CLIENT", "🔴 Terjadi kesalahan saat merakit URL: ${e.message}")
        }

    }

    fun disconnect() {
        webSocket?.close(1000, "User menutup aplikasi")
        webSocket = null
    }
}