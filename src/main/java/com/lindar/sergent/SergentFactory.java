package com.lindar.sergent;

public final class SergentFactory {
    
    public static Sergent newInstance() {
        return new Sergent();
    }

    private SergentFactory() {
    }

}
