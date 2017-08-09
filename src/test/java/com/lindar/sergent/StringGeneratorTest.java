package com.lindar.sergent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringGeneratorTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void randAlphabeticString() {
        StringGenerator generator = new StringGenerator();
        Assertions.assertTrue(generator.alphabetic().randString().matches("[a-zA-Z]+"), "String contains more than just alphabetic chars");
    }

    @Test
    void randAlphaNumericString() {
        StringGenerator generator = new StringGenerator();
        Assertions.assertTrue(generator.alphanumeric().randString().matches("[a-zA-Z1-9]+"), "String contains more than just alphanumeric chars");
    }

    @Test
    void randNumericString() {
        StringGenerator generator = new StringGenerator();
        System.out.println(generator.numeric().randString());
        Assertions.assertTrue(generator.numeric().randString().matches("[1-9]+"), "String contains more than just numeric chars");
    }

    @Test
    void randUppercaseString() {
        StringGenerator generator = new StringGenerator();
        Assertions.assertTrue(generator.uppercase().randString().matches("[A-Z]+"), "String contains more than just uppercase chars");
    }

    @Test
    void randLowercaseString() {
        StringGenerator generator = new StringGenerator();
        Assertions.assertTrue(generator.lowercase().randString().matches("[a-z]+"), "String contains more than just lowercase chars");
    }

}