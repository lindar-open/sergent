package com.lindar.sergent;

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
        ListGenerator listGenerator = sergent.listGenerator().ofSize(20).unique().withMinAndMax(10,20);
        System.out.println(listGenerator.randIntegers());
    }
}
