package com.fahrul.pcmonitorandroid.network.blueprint

import com.google.gson.annotations.SerializedName

data class SystemStatsResponse(
    val type: String,
    val data: SystemStatsData?
)

data class SystemStatsData(
    @SerializedName("cpu_usage") val cpuUsage: Double,
    @SerializedName("cpu_temp") val cpuTemp: Double,
    @SerializedName("ram_total") val ramTotal: Double,
    @SerializedName("ram_used") val ramUsed: Double
)