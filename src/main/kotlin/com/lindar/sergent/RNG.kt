package com.lindar.sergent

interface RNG {

    /**
     * Discards a random number of numbers from the RNG
     */
    fun discardNumbers(): Unit

    /**
     * Returns a random integer between 0 and Int.MAX_VALUE
     */
    fun nextInt(): Int

    /**
     * Returns a random integer between 0 and max (exclusive)
     */
    fun nextInt(max: kotlin.Int): Int

    /**
     * Returns a random integer between min (inclusive) and max (exclusive)
     */
    fun nextInt(min: kotlin.Int, max: kotlin.Int): Int

    /**
     * Shuffles the list in place
     */
    fun <T> shuffleList(listToShuffle: kotlin.collections.MutableList<T>): Unit

    /**
     * Returns a shuffled copy of the list
     */
    fun <T> shuffledCopy(list: kotlin.collections.List<T>): List<T>

    /**
     * Returns a shuffled list of all the integers between min (inclusive) and max (inclusive also)
     */
    fun shuffledIntArray(min: kotlin.Int, max: kotlin.Int): IntArray

}