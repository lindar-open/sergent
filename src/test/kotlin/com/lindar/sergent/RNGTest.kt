package com.lindar.sergent

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class RNGCoreTest {

    private lateinit var rngCore: SergentRNG

    @BeforeEach
    fun setUp() {
        rngCore = SergentRNG()
        rngCore.haltMonitor() // Assuming this stops external dependencies for isolated testing
    }

    @Test
    fun `nextInt should return an integer`() {
        //Our test here will be just to check if we can call this method
        rngCore.nextInt()
    }

    @Test
    fun `nextInt with max should return an integer within bounds`() {
        //Our test here will be just to check if we can call this method
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
        val list = (1..1000).toMutableList()
        val original = list.toList()
        rngCore.shuffleList(list)
        Assertions.assertEquals(1000, list.size)
        assertNotEquals(original, list)
        assertTrue(list.all { it in 1..1000 })
    }

    @Test
    fun `shuffledCopy should return a shuffled copy of the list`() {
        val list = (1..1000).toList()
        val shuffledList = rngCore.shuffledCopy(list)
        Assertions.assertEquals(list.size, shuffledList.size)
        assertNotEquals(list, shuffledList)
        assertTrue(list.all { it in 1..1000 })
    }

    @Test
    fun `shuffledIntArray should return a shuffled array of the specified range`() {
        val min = 1
        val max = 1000
        val result = rngCore.shuffledIntArray(min, max)
        Assertions.assertEquals((max - min) + 1, result.size)
        assertTrue(result.all { it in 1..1000 })
    }

    @Test
    fun `discardNumbers should not throw an exception`() {
        Assertions.assertDoesNotThrow { rngCore.discardNumbers() }
    }

    @Test
    fun `Shuffle a deck of cards`() {
        val deck = (1..52).toMutableList()
        val original = deck.toList()
        rngCore.shuffleList(deck)
        Assertions.assertEquals(52, deck.size)
        assertNotEquals(original, deck)
    }

}
