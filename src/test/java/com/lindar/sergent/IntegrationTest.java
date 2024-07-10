package com.lindar.sergent;

import java.util.List;

public class IntegrationTest {
    public static void main(String[] args) {
        Sergent sergent = SergentFactory.newInstance();
        IntGenerator intGenerator = sergent.intGenerator();
        System.out.println(intGenerator.randInt());
        System.out.println(intGenerator.randInt());
        System.out.println(intGenerator.randInt());
        System.out.println(intGenerator.randInt());
        System.out.println(intGenerator.randInt());
        System.out.println(intGenerator.randInt());
        intGenerator = intGenerator.withMax(100);
        System.out.println(intGenerator.randInt());
        System.out.println(intGenerator.randInt());
        System.out.println(intGenerator.randInt());
        System.out.println(intGenerator.randInt());
        System.out.println(intGenerator.randInt());
        System.out.println(intGenerator.randInt());
        System.out.println(intGenerator.randInt());
        ListGenerator listGenerator = sergent.listGenerator().ofSize(10).unique().withMinAndMax(10,20);
        List<Integer> mylist = listGenerator.randIntegers();
        System.out.println(mylist);
        Shuffler shuffler = sergent.shuffle();
        shuffler.list(mylist);
        System.out.println(mylist);
    }
}
