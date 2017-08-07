package com.lindar.sergent;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.text.CharacterPredicate;
import org.apache.commons.text.RandomStringGenerator;

import java.util.ArrayList;
import java.util.List;

public class StringGenerator {

    private final UniformRandomProvider randomProvider;

    StringGenerator(UniformRandomProvider randomProvider) {
        this.randomProvider = randomProvider;
    }

    private int length = 10;

    private int minCodePoint;
    private int maxCodePoint;

    private boolean alphabetic;
    private boolean numeric;
    private boolean lowercase;
    private boolean uppercase;

    public StringGenerator length(int length) {
        this.length = length;
        return this;
    }

    public StringGenerator withMinAndMaxCodePoint(int min, int max) {
        this.minCodePoint = min;
        this.maxCodePoint = max;
        return this;
    }

    public StringGenerator alphabetic() {
        this.alphabetic = true;
        return this;
    }

    public StringGenerator lowercase() {
        this.lowercase = true;
        return this;
    }

    public StringGenerator uppercase() {
        this.uppercase = true;
        return this;
    }

    public StringGenerator alphanumeric() {
        this.alphabetic = true;
        this.numeric = true;
        return this;
    }

    public StringGenerator numeric() {
        this.numeric = true;
        return this;
    }

    public String randString() {
        List<CharacterPredicate> predicates = new ArrayList<>();

        CharacterPredicate uppercasePred = c -> (c >= 'A' && c <= 'Z');
        CharacterPredicate lowercasePred = c -> (c >= 'a' && c <= 'z');
        CharacterPredicate numericPred = c -> (c >= '1' && c <= '9');

        if (alphabetic || lowercase || uppercase) {
            if (lowercase) predicates.add(lowercasePred);
            if (uppercase) predicates.add(uppercasePred);
            if (!lowercase && !uppercase) {
                predicates.add(lowercasePred);
                predicates.add(uppercasePred);
            }
        }

        if (numeric) {
            predicates.add(numericPred);
        }

        RandomStringGenerator.Builder builder = new RandomStringGenerator.Builder().usingRandom(randomProvider::nextInt);
        if (minCodePoint > 0 && maxCodePoint > 0) {
            builder.withinRange(minCodePoint, maxCodePoint);
        }
        if (!predicates.isEmpty()) {
            builder.filteredBy(predicates.toArray(new CharacterPredicate[]{}));
        }
        return builder.build().generate(length);
    }
}


