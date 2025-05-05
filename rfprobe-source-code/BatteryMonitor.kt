
package com.datadog.rfprobe

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager

fun collectBatteryInfo(context: Context, config: Config): List<Metric> {
    val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
    val batteryStatus = context.registerReceiver(null, filter)
    val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
    val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
    val batteryPct = level * 100 / scale.toDouble()
    val timestamp = System.currentTimeMillis() / 1000

    return listOf(
        Metric("rfgun.battery_level", "gauge", listOf(listOf(timestamp, batteryPct)),
            listOf("device_id:${config.deviceId}", "warehouse_location:${config.warehouseLocation}"))
    )
}
