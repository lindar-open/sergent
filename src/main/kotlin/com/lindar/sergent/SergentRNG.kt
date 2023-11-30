package com.lindar.sergent

import com.lindar.sergent.internal.DEFAULT_ALGORITHM
import com.lindar.sergent.internal.NativePRNGNonBlockingSecureRandom
import com.lindar.sergent.monitor.RNGException
import com.lindar.sergent.monitor.RNGMonitor

class SergentRNG(algorithm: String = DEFAULT_ALGORITHM) : RNG {

    data class RNGHealth(val healthy: Boolean, val healthMessage: String) {
        companion object {
            val HEALTHY_STAGE = RNGHealth(true, "ok")
        }
    }

    private val rngInternal = NativePRNGNonBlockingSecureRandom(algorithm)

    private val monitor = RNGMonitor()

    private var healthState = RNGHealth.HEALTHY_STAGE

    override fun discardNumbers() {
        repeat(10 + this.nextInt(10)) { this.nextInt() }
    }

    override fun nextInt(): Int {
        healthCheck()
        return rngInternal.next()
    }

    override fun nextInt(max: Int): Int {
        healthCheck()
        return rngInternal.nextScaled(max)
    }

    override fun nextInt(min: Int, max: Int): Int {
        healthCheck()
        return min + rngInternal.nextScaled(max - min)
    }

    //In place list shuffling
    override fun <T> shuffleList(listToShuffle: MutableList<T>) {
        healthCheck()
        listToShuffle.shuffle(rngInternal.rng)
    }

    override fun <T> shuffledCopy(list: List<T>): List<T> {
        healthCheck()
        // Create a copy of the original list and shuffle it
        return list.toMutableList().apply {
            shuffle(rngInternal.rng)
        }
    }

    override fun shuffledIntArray(min: Int, max: Int): IntArray {
        healthCheck()
        return (min..max).toMutableList().apply { shuffle(rngInternal.rng) }.toIntArray()
    }

    //Method used when testing to avoid having the monitor running
    fun haltMonitor() = monitor.halt()

    private fun healthCheck() {
        if (!healthState.healthy) {
            throw RNGException(healthState.healthMessage)
        }
    }

}