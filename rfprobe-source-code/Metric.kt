
package com.datadog.rfprobe

data class Metric(
    val metric: String,
    val type: String = "gauge",
    val points: List<List<Any>>,
    val tags: List<String>
)
