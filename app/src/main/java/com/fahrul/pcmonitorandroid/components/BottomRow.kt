package com.fahrul.pcmonitorandroid.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

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
            Text(
                text = "Disconnect",
                color = Color.White
            )
        }
    }

    if (showRestartDialog) {
        Dialog(onDismissRequest = { showRestartDialog = false }) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Konfirmasi Restart",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Text(
                        text = "kamu yakin ingin me-restart PC ini? Aplikasi nya akan terputus karena server juga mati ketika pc di restart/matikan",
                        style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 15.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {showRestartDialog = false},
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                        ) {
                            Text(
                                text = "Batal",
                                color = Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                showRestartDialog = false
                                onRestart()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text(
                                text = "Ya, Restart",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}