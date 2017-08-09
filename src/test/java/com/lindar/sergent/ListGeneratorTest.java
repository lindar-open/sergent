package com.lindar.sergent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

class ListGeneratorTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void randUniqueIntegers() {
        ListGenerator listGenerator = new ListGenerator();
        int min = 2;
        int max = 90;
        List<Integer> generatedNumbers = listGenerator.withMinAndMax(min, max).unique().randIntegers();

        Assertions.assertEquals(max - min + 1, generatedNumbers.size(), "Not enough numbers generated");

        boolean hasAllNumbers = IntStream.rangeClosed(2, 90).boxed().allMatch(generatedNumbers::contains);

        Assertions.assertTrue(hasAllNumbers, "Numbers are missing from the list");
    }

}