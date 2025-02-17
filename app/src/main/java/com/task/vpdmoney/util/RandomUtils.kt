package com.task.vpdmoney.util

import java.util.UUID
import kotlin.random.Random
import kotlin.text.substring

object RandomUtils {
    fun generateRandomUsername(): String {
        return "user_${UUID.randomUUID().toString().substring(0, 8)}"
    }

    fun generateRandomAccountBalance(): Double {
        return Random.nextDouble(100.0, 10000.0) // Balance between 100 and 10000
    }
}