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

    private List<CharacterPredicate> predicates = new ArrayList<>();

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
        predicates.add(c -> (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'));
        return this;
    }

    public StringGenerator alphanumeric() {
        predicates.add(c -> (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'));
        predicates.add(c -> (c >= '1' && c <= '9'));
        return this;
    }

    public StringGenerator numeric() {
        predicates.add(c -> (c >= '1' && c <= '9'));
        return this;
    }

    public String randString() {
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
