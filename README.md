# *SE*cure *R*andom *GEN*era*T*or Kotlin library

Sergent is a versatile and secure random number generation (RNG) library for Kotlin, now in its 3.0 release. This latest version has been fully ported to Kotlin and Gradle, offering a more streamlined and user-friendly experience for developers.

## Features

- **Security**: Sergent is built with security as a priority. By default, it uses the `NativePRNGNonBlocking` algorithm, which is cryptographically secure and efficient.
- **Flexibility**: While the default algorithm is recommended for most use cases, Sergent allows users to specify any other algorithm supported by `SecureRandom`. This makes Sergent adaptable to a wide range of cryptographic needs.
- **NIST Compliance**: The RNG complies with the NIST RNG test harnesses, ensuring reliable and secure random number generation.
- **RNG Monitoring**: Sergent includes an advanced monitoring system that performs continuous CHI-Square tests. This ensures the integrity and randomness of the numbers generated, with error handling to transition the RNG into an error state if required.

## Installation

Gradle:

```kotlin
dependencies {
    implementation("com.lindar:sergent:3.0.0")
}
```

## Usage

Sergent provides a simple and intuitive interface for RNG operations. Here's a quick overview of the functionalities:

```kotlin
interface RNG {

    // Discards a random number of numbers from the RNG
    fun discardNumbers()

    // Returns a random integer between 0 and Int.MAX_VALUE
    fun nextInt(): Int

    // Returns a random integer between 0 and max (exclusive)
    fun nextInt(max: Int): Int

    // Returns a random integer between min (inclusive) and max (exclusive)
    fun nextInt(min: Int, max: Int): Int

    // Shuffles the list in place
    fun <T> shuffleList(listToShuffle: MutableList<T>)

    // Returns a shuffled copy of the list
    fun <T> shuffledCopy(list: List<T>): List<T>

    // Returns a shuffled list of all the integers between min (inclusive) and max (inclusive also)
    fun shuffledIntArray(min: Int, max: Int): IntArray
}
```

```kotlin
import com.lindar.sergent.SergentRNG
// Create a new RNG instance
val rng = SergentRNG()

rng.nextInt()
```

## RNG Monitoring

Monitoring functionality for RNG.

* It performs a CHI-Square test in the background generating numbers from the given RNG every 500 millis.
* If the test fails more than 3 times consecutively, the RNG will enter an error state and halt number generation.
* This is an exceptional case and should not normally occur.


## License
Apache License Version 2.0 (See [LICENSE](LICENSE) for details).

2023 - Lindar Ltd

