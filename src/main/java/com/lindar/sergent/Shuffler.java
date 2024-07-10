package com.lindar.sergent;

import java.util.List;

public class Shuffler {
    private SergentRNG sergentRNG = new SergentRNG();

    public Shuffler() {
    }

    public <T> void list(List<T> list) {
        sergentRNG.shuffleList(list);
    }
}
