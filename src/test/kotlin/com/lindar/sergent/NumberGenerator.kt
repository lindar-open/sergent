package com.lindar.sergent

import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun main(args: Array<String>) {
    //This method will generate numbers and save them into a file
    val rng = SergentRNG()
    repeat(4) {
        generateNumbers(rng, 100_000_000, "numbers.bin")
        print(".")
    }

}

fun generateNumbers(rng: SergentRNG, i: Int, s: String) {
    val file = java.io.File(s)
    FileOutputStream(file, true).use { fos ->
        val buffer = ByteBuffer.allocate(4) // Assuming integers are 4 bytes
        buffer.order(ByteOrder.LITTLE_ENDIAN) // Set buffer to Little Endian
        repeat(i) {
            buffer.clear()
            buffer.putInt(rng.nextInt())
            fos.write(buffer.array())
        }
    }
}
