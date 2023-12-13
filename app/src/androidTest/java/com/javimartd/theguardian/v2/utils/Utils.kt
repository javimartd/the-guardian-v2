package com.javimartd.theguardian.v2.utils

import java.util.*
import java.util.concurrent.ThreadLocalRandom

fun randomInt() = ThreadLocalRandom.current().nextInt(0, 1000 + 1)

fun randomLong() = randomInt().toLong()

fun randomString() = UUID.randomUUID().toString()

fun randomBoolean()= Math.random() < 0.5