package com.fahrul.pcmonitorandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.fahrul.pcmonitorandroid.components.ConnectionScreen
import com.fahrul.pcmonitorandroid.ui.theme.PCmonitorandroidTheme
import androidx.compose.runtime.getValue
import com.fahrul.pcmonitorandroid.network.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PCmonitorandroidTheme {
                val viewModel: MainViewModel = viewModel()

                val stats by viewModel.stats.collectAsState()
                val connectionStatus by viewModel.connectionStatus.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (connectionStatus == "Tersambung"){
                        DashboardScreen(
                            modifier = Modifier.padding(innerPadding),
                            stats = stats,
                            status = connectionStatus,
                            onDisconnect = {
                                viewModel.disconnect()
                            }
                        )
                    } else {
                        ConnectionScreen(
                            modifier = Modifier.padding(innerPadding),
                            status = connectionStatus,
                            onConnectClicked = { ip, _ ->
                                viewModel.connectToPc(ip)
                            }
                        )
                    }
                }
            }
        }
    }
}