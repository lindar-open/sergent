package com.lindar.sergent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StringGeneratorTest {

    @Before
    public void setUp() {
    }

    @Test
    public void randAlphabeticString() {
        StringGenerator generator = SergentFactory.newInstance().stringGenerator();
        Assert.assertTrue("String contains more than just alphabetic chars", generator.alphabetic().randString().matches("[a-zA-Z]+"));
    }

    @Test
    public void randAlphaNumericString() {
        StringGenerator generator = SergentFactory.newInstance().stringGenerator();
        Assert.assertTrue("String contains more than just alphanumeric chars", generator.alphanumeric().randString().matches("[a-zA-Z1-9]+"));
    }

    @Test
    public void randNumericString() {
        StringGenerator generator = SergentFactory.newInstance().stringGenerator();
        Assert.assertTrue("String contains more than just numeric chars", generator.numeric().randString().matches("[1-9]+"));
    }

    @Test
    public void randUppercaseString() {
        StringGenerator generator = SergentFactory.newInstance().stringGenerator();
        Assert.assertTrue("String contains more than just uppercase chars", generator.uppercase().randString().matches("[A-Z]+"));
    }

    @Test
    public void randLowercaseString() {
        StringGenerator generator = SergentFactory.newInstance().stringGenerator();
        Assert.assertTrue("String contains more than just lowercase chars", generator.lowercase().randString().matches("[a-z]+"));
    }

}