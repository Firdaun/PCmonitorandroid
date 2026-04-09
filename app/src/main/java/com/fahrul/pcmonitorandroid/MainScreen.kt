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
import com.fahrul.pcmonitorandroid.ui.theme.PCmonitorandroidTheme

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Pc connected",
            color = Color(0xFF4CAF50),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        MonitorCard(title = "Suhu CPU", value = "50°C")
        Spacer(modifier = Modifier.height(16.dp))
        MonitorCard(title = "CPU Usage", value = "12.5 %")
        Spacer(modifier = Modifier.height(16.dp))
        MonitorCard(title = "RAM", value = "4.5 / 15.6 GB")

        Spacer(modifier = Modifier.weight(1f))

        BottomRow()
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
        ConnectionScreen()
    }
}