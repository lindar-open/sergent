package com.lindar.sergent;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.text.CharacterPredicate;
import org.apache.commons.text.RandomStringGenerator;

import java.util.ArrayList;
import java.util.List;

public class StringGenerator {
    private Long randomProviderSeed;

    private int length = 10;

    private int minCodePoint;
    private int maxCodePoint;

    private boolean alphabetic;
    private boolean numeric;
    private boolean lowercase;
    private boolean uppercase;

    public StringGenerator withSeed(Long seed) {
        return buildCopy().seed(seed).build();
    }

    public StringGenerator length(int length) {
        return buildCopy().length(length).build();
    }

    public StringGenerator withMinAndMaxCodePoint(int min, int max) {
        return buildCopy().minCodePoint(min).maxCodePoint(max).build();
    }

    public StringGenerator alphabetic() {
        return buildCopy().alphabetic(true).build();
    }

    public StringGenerator lowercase() {
        return buildCopy().alphabetic(true).lowercase(true).build();
    }

    public StringGenerator uppercase() {
        return buildCopy().alphabetic(true).uppercase(true).build();
    }

    public StringGenerator alphanumeric() {
        return buildCopy().alphabetic(true).numeric(true).build();
    }

    public StringGenerator numeric() {
        return buildCopy().numeric(true).build();
    }

    public String randString() {
        UniformRandomProvider randomProvider = RandomProviderFactory.getInstance(this.randomProviderSeed);

        List<CharacterPredicate> predicates = new ArrayList<>();

        CharacterPredicate uppercasePred = c -> (c >= 'A' && c <= 'Z');
        CharacterPredicate lowercasePred = c -> (c >= 'a' && c <= 'z');
        CharacterPredicate numericPred   = c -> (c >= '1' && c <= '9');

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
        } else {
            int minCodePoint = Character.MIN_CODE_POINT;
            int maxCodePoint = Character.MAX_CODE_POINT;
            if (numeric) {
                if('1' < minCodePoint){
                    minCodePoint = '1';
                }

                if('9' > maxCodePoint){
                    maxCodePoint = '9';
                }
            }

            if (alphabetic || lowercase || uppercase) {
                if (lowercase) {
                    if('a' < minCodePoint){
                        minCodePoint = 'a';
                    }

                    if('z' > maxCodePoint){
                        maxCodePoint = 'z';
                    }
                }
                if (uppercase) {
                    if('A' < minCodePoint){
                        minCodePoint = 'A';
                    }

                    if('Z' > maxCodePoint){
                        maxCodePoint = 'Z';
                    }
                }
                if (!lowercase && !uppercase) {
                    if('A' < minCodePoint){
                        minCodePoint = 'A';
                    }

                    if('z' > maxCodePoint){
                        maxCodePoint = 'z';
                    }
                }
            }

            builder.withinRange(minCodePoint, maxCodePoint);
        }

        if (!predicates.isEmpty()) {
            builder.filteredBy(predicates.toArray(new CharacterPredicate[]{}));
        }
        return builder.build().generate(length);
    }



    StringGenerator(int length, int minCodePoint, int maxCodePoint, boolean alphabetic,
                           boolean numeric, boolean lowercase, boolean uppercase, Long randomProviderSeed) {
        this.randomProviderSeed = randomProviderSeed;
        this.length = length;
        this.minCodePoint = minCodePoint;
        this.maxCodePoint = maxCodePoint;
        this.alphabetic = alphabetic;
        this.numeric = numeric;
        this.lowercase = lowercase;
        this.uppercase = uppercase;
    }

    StringGenerator() {
    }

    private StringGeneratorBuilder buildCopy() {
        return new StringGeneratorBuilder().seed(this.randomProviderSeed).length(this.length).alphabetic(this.alphabetic).numeric(this.numeric)
                .lowercase(this.lowercase).uppercase(this.uppercase).minCodePoint(this.minCodePoint).maxCodePoint(this.maxCodePoint);
    }

    public static class StringGeneratorBuilder {
        private Long randomProviderSeed;

        private int length = 10;
        private int minCodePoint;
        private int maxCodePoint;
        private boolean alphabetic;
        private boolean numeric;
        private boolean lowercase;
        private boolean uppercase;

        StringGenerator.StringGeneratorBuilder seed(Long randomProviderSeed) {
            this.randomProviderSeed = randomProviderSeed;
            return this;
        }

        StringGenerator.StringGeneratorBuilder length(int length) {
            this.length = length;
            return this;
        }

        StringGenerator.StringGeneratorBuilder minCodePoint(int minCodePoint) {
            this.minCodePoint = minCodePoint;
            return this;
        }

        StringGenerator.StringGeneratorBuilder maxCodePoint(int maxCodePoint) {
            this.maxCodePoint = maxCodePoint;
            return this;
        }

        StringGenerator.StringGeneratorBuilder alphabetic(boolean alphabetic) {
            this.alphabetic = alphabetic;
            return this;
        }

        StringGenerator.StringGeneratorBuilder numeric(boolean numeric) {
            this.numeric = numeric;
            return this;
        }

        StringGenerator.StringGeneratorBuilder lowercase(boolean lowercase) {
            this.lowercase = lowercase;
            return this;
        }

        StringGenerator.StringGeneratorBuilder uppercase(boolean uppercase) {
            this.uppercase = uppercase;
            return this;
        }

        StringGenerator build() {
            return new StringGenerator(length, minCodePoint, maxCodePoint, alphabetic, numeric, lowercase, uppercase, randomProviderSeed);
        }

        public String toString() {
            return "com.lindar.sergent.generators.StringGenerator.StringGeneratorBuilder(length=" + this.length + ", minCodePoint=" + this.minCodePoint + ", maxCodePoint=" + this.maxCodePoint + ", alphabetic=" + this.alphabetic + ", numeric=" + this.numeric + ", lowercase=" + this.lowercase + ", uppercase=" + this.uppercase + ")";
        }
    }
}


