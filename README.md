# *SE*cure *R*andom *GEN*era*T*or java library

Sergent factory supports: 
- java.security.SecureRandom
- [org.apache.commons.math3.random.MersenneTwister](http://commons.apache.org/proper/commons-math/javadocs/api-3.3/org/apache/commons/math3/random/MersenneTwister.html)
- [org.apache.commons.math3.random.ISAACRandom](http://commons.apache.org/proper/commons-math/javadocs/api-3.3/org/apache/commons/math3/random/ISAACRandom.html)
- java.util.Random

*Quick Start:*

```java
Sergent rng = SergentFactory.getInstance();
int randInt = rng.randInt();
long randLong = rng.randLong();
List<Integer> fiveUniqueRandNumbersFromZeroToTen = rng.randIntList(0, 10, 5, true);
List<Integer> fiveUniqueRandNumbersFromZeroToTenWithProps = rng.randIntList(new SequenceProps().min(0).max(10).size(5).unique(true));
// a unique (no duplicates) random integer list starting from 1 to [max]
List<Integer> uniqueSequence = rng.uniformSequence(10_000);
```
