package com.fahrul.pcmonitorandroid.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BottomRow(
    onDisconnect: () -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { /* TODO: Panggil API Lock */ }) {
            Text("Lock")
        }

        Button(onClick = { /* TODO: Panggil API Lock */ }) {
            Text("Restart")
        }
        Button(
            onClick = { onDisconnect() }, // Sementara dipakai untuk disconnect
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Disconnect") // Ubah teks sementara
        }
    }
}