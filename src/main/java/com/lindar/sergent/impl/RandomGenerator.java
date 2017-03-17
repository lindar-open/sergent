package com.lindar.sergent.impl;

public interface RandomGenerator {
    int nextInt();
    int nextInt(int bound);
    
    long nextLong();
    long nextLong(long bound);
}
