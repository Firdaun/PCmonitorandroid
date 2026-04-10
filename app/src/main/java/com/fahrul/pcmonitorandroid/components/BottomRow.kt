package com.fahrul.pcmonitorandroid.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BottomRow(
    onLock: () -> Unit,
    onRestart: () -> Unit,
    onDisconnect: () -> Unit
){
    var showRestartDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { onLock() }) {
            Text("Lock")
        }

        Button(onClick = { showRestartDialog = true }) {
            Text("Restart")
        }
        Button(
            onClick = { onDisconnect() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Disconnect")
        }
    }

    if (showRestartDialog) {
        AlertDialog(
            onDismissRequest = {
                // Jika user memencet area luar pop-up, tutup pop-up nya
                showRestartDialog = false
            },
            title = {
                Text(text = "Konfirmasi Restart")
            },
            text = {
                Text(text = "Apakah kamu yakin ingin me-restart PC ini?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showRestartDialog = false // Tutup pop-up
                        onRestart()               // EKSEKUSI! Suruh kurir berangkat ke PC
                    }
                ) {
                    Text("Ya, Restart")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showRestartDialog = false // Batal, cukup tutup pop-up
                    }
                ) {
                    Text("Batal")
                }
            }
        )
    }
}