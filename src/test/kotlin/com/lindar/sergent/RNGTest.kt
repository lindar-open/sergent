package com.lindar.sergent

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RNGCoreTest {

    private lateinit var rngCore: SergentRNG

    @BeforeEach
    fun setUp() {
        rngCore = SergentRNG()
        rngCore.haltMonitor() // Assuming this stops external dependencies for isolated testing
    }

    @Test
    fun `nextInt should return an integer`() {
        rngCore.nextInt()
    }

    @Test
    fun `nextInt with max should return an integer within bounds`() {
        val max = 100
        val result = rngCore.nextInt(max)
        Assertions.assertTrue(result in 0 until max)
    }

    @Test
    fun `nextInt with min and max should return an integer within bounds`() {
        val min = 50
        val max = 100
        val result = rngCore.nextInt(min, max)
        Assertions.assertTrue(result in min until max)
    }

    @Test
    fun `shuffleList should shuffle the list`() {
        val list = mutableListOf(1, 2, 3, 4, 5)
        rngCore.shuffleList(list)
        Assertions.assertEquals(5, list.size)
    }

    @Test
    fun `shuffledCopy should return a shuffled copy of the list`() {
        val originalList = listOf(1, 2, 3, 4, 5)
        val shuffledList = rngCore.shuffledCopy(originalList)
        Assertions.assertEquals(originalList.size, shuffledList.size)
    }

    @Test
    fun `shuffledIntArray should return a shuffled array of the specified range`() {
        val min = 1
        val max = 5
        val result = rngCore.shuffledIntArray(min, max)
        Assertions.assertEquals((max - min) + 1, result.size)
    }

    @Test
    fun `discardNumbers should not throw an exception`() {
        Assertions.assertDoesNotThrow { rngCore.discardNumbers() }
    }

}
