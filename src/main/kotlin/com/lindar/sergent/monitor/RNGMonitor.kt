package com.lindar.sergent.monitor

import kotlin.concurrent.thread
import kotlin.math.pow

/**
 * Monitoring functionality for RNG.
 *
 * It performs a CHI-Square test in the background generating numbers from the given RNG every 500 millis.
 *
 * When the amount of numbers generated is big enough then the sets is performed, if the test fails more than 3 times
 * consecutively, then the RNG will go into an error state and won't be able to produce any other number.
 *
 * This case is a very exceptional case and should not happen.
 */
class RNGMonitor {
    companion object {
        private const val MASK = 255
        private const val CHI_SQUARE_THRESHOLD = 330.51
        private const val MAX_CONSECUTIVE_FAILURES = 3
    }

    private val listToEvaluate: MutableList<Int> = mutableListOf()
    private var consecutiveFailures = 0
    private var isRunningMonitor = false

    fun monitor(rngOperation: () -> Int, errorReporter: (String) -> Unit, millisBetweenNumbers: Long = 500L) {
        isRunningMonitor = true
        thread {
            while (isRunningMonitor) {
                addNumber(rngOperation(), errorReporter)
                Thread.sleep(millisBetweenNumbers)
            }
        }
    }

    fun halt() {
        this.isRunningMonitor = false
    }

    private fun addNumber(next: Int, errorReporter: (String) -> Unit) {
        listToEvaluate.add(next)
        if (listToEvaluate.size >= (MASK + 1) * 16) {
            performChiSquareTest(listToEvaluate, errorReporter)
            listToEvaluate.clear()
        }
    }

    /**
     * Performing a chi-square test on the full 32 bit output may not be a feasible solution because for the test to become
     * meaningful, the sample size used for the test should be at least 5 or preferably 10 times the number of possible value.
     *
     *
     * Split the 32-bit number into subgroups (say 4 groups of 8 bits each) and performing the test on each group independently.
     * Since an 8-bit number can fall only among 256 possible values, one can do a meaningful test with 2560 numbers.
     */
    private fun performChiSquareTest(listToEvaluate: List<Int>, errorReporter: (String) -> Unit) {
        //FOR EVERY TEST WE CLEAN UP
        val chiCounter = Array(4) { IntArray(MASK + 1) } //4 GROUPS WITH ALL THE POSSIBLE NUMBERS EACH (4 COUNTERS WITH 256 ELEMENTS)
        //8 Bit size split
        for (number in listToEvaluate) {
            for (j in 0..3) {
                //FREQUENCY CALCULATION
                chiCounter[j][number shr 8 * j and MASK]++
            }
        }
        for (i in 0..3) {
            val chiSquare = calculateChiSquare(chiCounter[i], (listToEvaluate.size / (MASK + 1)).toDouble())
            if (chiSquare >= CHI_SQUARE_THRESHOLD) {
                consecutiveFailures++
                println("Check fail number: $consecutiveFailures")
                if (consecutiveFailures > MAX_CONSECUTIVE_FAILURES) {
                    errorReporter("Invalid Chi-Square value computed $chiSquare is bigger than the threshold $CHI_SQUARE_THRESHOLD")
                }
            } else {
                consecutiveFailures = 0
            }
        }
    }

    private fun calculateChiSquare(counter: IntArray, ex: Double): Double {
        var chiSquare = 0.0
        for (i in counter.indices) {
            chiSquare += (counter[i] - ex).pow(2.0) / ex
        }
        return chiSquare
    }
}
