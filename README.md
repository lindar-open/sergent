# *SE*cure *R*andom *GEN*era*T*or java library

Sergent now supports background cycling and external configuration: **sergent-configs.properties**
If you add Sergent as a dependency, all you have to do is create a *sergent-configs.properties* file in your classpath (preferably under src/main/resources) and set your own random implementation or background cycling configs. 

And example configs file can be found here: https://github.com/lindar-open/sergent/blob/master/src/main/resources/sergent-configs-example.properties


**Quick Start:**

```java
Sergent rng = SergentFactory.newInstance();

// Int Generator
int randInt = rng.intGenerator().randInt();
int randIntWithOptions = rng.intGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(-9,-8,-7,-6,-5)).randInt();

// Long Generator
long randLong = rng.longGenerator().randLong();
long randLongWithOptions = rng.longGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(-9,-8,-7,-6,-5)).randLong();

// List Generator
List<Integer> fiveUniqueRandNumbersFromOneToTen = rng.listGenerator().withMinAndMax(1, 10).ofSize(5).unique().randIntegers();
// a unique (no duplicates) random integer list starting from 1 to 90
List<Integer> uniqueSequence = rng.listGenerator().withMinAndMax(1, 90).unique(true).randIntegers();

// String Generator
String alphabeticUppercaseString = rng.stringGenerator().alphabetic().uppercase().randString();
String alphaNumericString = rng.stringGenerator().alphanumeric().randString();
String numericString = rng.stringGenerator().numeric().randString();
String alphaNumericUppercaseOnlyString = rng.stringGenerator().alphanumeric().uppercase().randString();

// List or array Shuffler
List<Integer> numbers = IntStream.rangeClosed(1, 100).boxed().collect(Collectors.toList());
rng.shuffle().list(numbers); // the numbers list is now shuffled randmly
// you can go even further and shuffle only parts of the list by specifying where to start and the direction
rng.shuffle().start(50).towardHead().list(numbers); // will shuffle the numbers from 1 to 50
rng.shuffle().start(50).towardTail().list(numbers); // will shuffle the numbers from 50 to 100
```

**NOTE: All above generators use the same Sergent instance with the same initial configs and seed. If you want to reseed, create a new instance and use that.**

For more examples please check the unit tests: https://github.com/lindar-open/sergent/tree/master/src/test/java/com/lindar/sergent

Usage: 

```xml
<dependency>
    <groupId>com.lindar</groupId>
    <artifactId>sergent</artifactId>
    <version>2.1.5</version>
</dependency>
```
