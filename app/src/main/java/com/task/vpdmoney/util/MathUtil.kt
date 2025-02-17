package com.task.vpdmoney.util

fun Double.formatValue(): Double {
    return Math.round((this) * 100.0) / 100.0
}