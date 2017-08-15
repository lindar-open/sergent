package com.lindar.sergent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

public class ListGeneratorTest {

    @Before
    public void setUp() {
    }

    @Test
    public void randUniqueIntegers() {
        ListGenerator listGenerator = SergentFactory.newInstance().listGenerator();
        int min = 2;
        int max = 90;
        List<Integer> generatedNumbers = listGenerator.withMinAndMax(min, max).unique().randIntegers();

        Assert.assertEquals("Not enough numbers generated", max - min + 1, generatedNumbers.size());

        boolean hasAllNumbers = IntStream.rangeClosed(2, 90).boxed().allMatch(generatedNumbers::contains);

        Assert.assertTrue("Numbers are missing from the list", hasAllNumbers);
    }

}