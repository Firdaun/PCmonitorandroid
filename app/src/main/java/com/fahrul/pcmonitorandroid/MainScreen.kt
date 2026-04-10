package com.fahrul.pcmonitorandroid

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fahrul.pcmonitorandroid.components.BottomRow
import com.fahrul.pcmonitorandroid.components.ConnectionScreen
import com.fahrul.pcmonitorandroid.components.MonitorCard
import com.fahrul.pcmonitorandroid.network.blueprint.SystemStatsData
import com.fahrul.pcmonitorandroid.ui.theme.PCmonitorandroidTheme

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    stats: SystemStatsData?,
    status: String,
    onLock: () -> Unit,
    onRestart: () -> Unit,
    onDisconnect: () -> Unit
){
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = status,
            color = if (status == "Tersambung") Color(0xFF4CAF50) else Color.Red,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        val cpuTemp = stats?.cpuTemp?.toString()?: "__"
        val cpuUsage = stats?.cpuUsage?.toString()?: "__"
        val ramUsed = stats?.ramUsed?.toString()?: "__"
        val ramTotal = stats?.ramTotal?.toString()?: "__"

        MonitorCard(title = "Suhu CPU", value = "$cpuTemp °C")
        Spacer(modifier = Modifier.height(16.dp))
        MonitorCard(title = "CPU Usage", value = "$cpuUsage %")
        Spacer(modifier = Modifier.height(16.dp))
        MonitorCard(title = "RAM", value = "$ramUsed / $ramTotal GB")

        Spacer(modifier = Modifier.weight(1f))

        BottomRow(onDisconnect = onDisconnect, onLock = onLock, onRestart = onRestart)
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_6_PRO
)
@Composable
fun GreetingPreview() {
    PCmonitorandroidTheme {
        val connectionStatus = ""
        ConnectionScreen(
            status = connectionStatus,
            onConnectClicked = { _, _ -> }
        )
    }
}