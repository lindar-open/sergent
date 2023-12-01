package com.lindar.sergent.internal

import java.security.SecureRandom

class NativePRNGNonBlockingSecureRandom(algorithm: String) : InternalRNG(algorithm) {

    val rng: SecureRandom = try {
        SecureRandom.getInstance(algorithm)
    } catch (e: Exception) {
            SecureRandom.getInstanceStrong()
    }

    override fun reseed() {
        rng.setSeed(seeder.nextLong())
    }

    override fun next(): Int {
        return rng.nextInt()
    }

    override fun nextScaled(max: Int): Int {
        return rng.nextInt(max)
    }

}