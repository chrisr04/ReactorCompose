package com.animotionsoftware.lib.reactor.util

import java.util.concurrent.atomic.AtomicInteger

private const val INITIAL_ID_VALUE = 0
private const val SAFETY_MARGIN_VALUE = 100
private const val MAX_ID = Int.MAX_VALUE - SAFETY_MARGIN_VALUE

internal class IdGenerator {
    private val counter = AtomicInteger(INITIAL_ID_VALUE)

    fun nextId(): Int {
        if (counter.get() < MAX_ID) return counter.incrementAndGet()
        counter.set(INITIAL_ID_VALUE)
        return counter.get()
    }
}