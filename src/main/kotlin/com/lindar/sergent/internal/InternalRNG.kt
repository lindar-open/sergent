package com.lindar.sergent.internal

import java.security.SecureRandom

const val DEFAULT_ALGORITHM = "NativePRNGNonBlocking"

/**
 * This is the internal class that generates the numbers
 */
abstract class InternalRNG(algorithm: String) {

    protected val seeder: SecureRandom = SecureRandom.getInstance(algorithm)

    init {
        //We force the seed creation in the seeder
        this.seeder.nextInt()
    }

    abstract fun reseed()
    abstract fun next(): Int
    abstract fun nextScaled(max: Int): Int
}