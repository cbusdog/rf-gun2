
package com.datadog.rfprobe

import android.app.ActivityManager
import android.content.Context
import android.os.Debug
import android.os.Process

fun collectCpuAndMemory(context: Context, config: Config): List<Metric> {
    val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val pid = Process.myPid()
    val memoryInfo = Debug.MemoryInfo()
    Debug.getMemoryInfo(memoryInfo)

    val totalPss = memoryInfo.totalPss // in KB

    val runtime = Runtime.getRuntime()
    val usedMem = (runtime.totalMemory() - runtime.freeMemory()) / 1024 // KB

    val timestamp = System.currentTimeMillis() / 1000

    return listOf(
        Metric("rfgun.memory.total_pss_kb", "gauge", listOf(listOf(timestamp, totalPss.toDouble())),
            listOf("device_id:${config.deviceId}", "warehouse_location:${config.warehouseLocation}")),
        Metric("rfgun.memory.used_mem_kb", "gauge", listOf(listOf(timestamp, usedMem.toDouble())),
            listOf("device_id:${config.deviceId}", "warehouse_location:${config.warehouseLocation}"))
    )
}
