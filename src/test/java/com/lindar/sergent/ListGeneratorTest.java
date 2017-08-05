package com.lindar.sergent;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

class ListGeneratorTest {
    private IntGenerator intGenerator;
    private Shuffler shuffler;

    @BeforeEach
    void setUp() {
        UniformRandomProvider provider = RandomSource.create(RandomSource.ISAAC);
        intGenerator = new IntGenerator(provider);
        shuffler = new Shuffler(provider);
    }

    @Test
    void randUniqueIntegers() {
        ListGenerator listGenerator = new ListGenerator(intGenerator, shuffler);
        int min = 2;
        int max = 90;
        List<Integer> generatedNumbers = listGenerator.withMinAndMax(min, max).unique().randIntegers();

        Assertions.assertEquals(max - min + 1, generatedNumbers.size(), "Not enough numbers generated");

        boolean hasAllNumbers = IntStream.rangeClosed(2, 90).boxed().allMatch(generatedNumbers::contains);

        Assertions.assertTrue(hasAllNumbers, "Numbers are missing from the list");
    }

}