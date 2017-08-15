package com.lindar.sergent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

class StringGeneratorTest {

    @Before
    void setUp() {
    }

    @Test
    void randAlphabeticString() {
        StringGenerator generator = new StringGenerator();
        Assert.assertTrue("String contains more than just alphabetic chars", generator.alphabetic().randString().matches("[a-zA-Z]+"));
    }

    @Test
    void randAlphaNumericString() {
        StringGenerator generator = new StringGenerator();
        Assert.assertTrue("String contains more than just alphanumeric chars", generator.alphanumeric().randString().matches("[a-zA-Z1-9]+"));
    }

    @Test
    void randNumericString() {
        StringGenerator generator = new StringGenerator();
        System.out.println(generator.numeric().randString());
        Assert.assertTrue("String contains more than just numeric chars", generator.numeric().randString().matches("[1-9]+"));
    }

    @Test
    void randUppercaseString() {
        StringGenerator generator = new StringGenerator();
        Assert.assertTrue("String contains more than just uppercase chars", generator.uppercase().randString().matches("[A-Z]+"));
    }

    @Test
    void randLowercaseString() {
        StringGenerator generator = new StringGenerator();
        Assert.assertTrue("String contains more than just lowercase chars", generator.lowercase().randString().matches("[a-z]+"));
    }

}