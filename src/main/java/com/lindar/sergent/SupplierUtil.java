package com.lindar.sergent;

import org.apache.commons.rng.UniformRandomProvider;

import java.util.function.IntSupplier;
import java.util.function.LongSupplier;

public class SupplierUtil {

    private SupplierUtil(){}

    public static IntSupplier randomValueIntSupplier(UniformRandomProvider randomProvider, int min, int max) {
        IntSupplier randomValueSupplier;
        if (min > 0 && max > 0) {
            randomValueSupplier = () -> (randomProvider.nextInt((max - min) + 1) + min);
        } else if (max > 0) {
            randomValueSupplier = () -> randomProvider.nextInt(max + 1);
        } else {
            randomValueSupplier = randomProvider::nextInt;
        }
        return randomValueSupplier;
    }

    public static LongSupplier randomValueLongSupplier(UniformRandomProvider randomProvider, long min, long max) {
        LongSupplier randomValueSupplier;
        if (min > 0 && max > 0) {
            randomValueSupplier = () -> (randomProvider.nextLong((max - min) + 1) + min);
        } else if (max > 0) {
            randomValueSupplier = () -> randomProvider.nextLong(max + 1);
        } else {
            randomValueSupplier = randomProvider::nextLong;
        }
        return randomValueSupplier;
    }
}
