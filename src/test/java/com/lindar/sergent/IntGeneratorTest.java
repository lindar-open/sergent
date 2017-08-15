package com.lindar.sergent;


import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class IntGeneratorTest {

    private Sergent sergent;

    @Before
    public void setUp() {
        sergent = SergentFactory.newInstance(0);
    }

    @Test
    //@DisplayName("Test using withMax")
    public void testWithMax(){
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMax(1), 10000), 0, 1);
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMax(10), 10000), 0, 10);
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMax(999), 10000), 0, 999);
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMax(Integer.MAX_VALUE - 1), 10000), 0, Integer.MAX_VALUE - 1);
    }

    @Test
    //@DisplayName("Test using withMax with invalid arguments")
    public void testWithMaxInvalidArguments(){
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.intGenerator().withMax(Integer.MAX_VALUE));//  "Max >= Integer.MAX_VALUE shouldn't be allowed");
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.intGenerator().withMax(Integer.MIN_VALUE));//  "Max less then 0 shouldn't be allowed");
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.intGenerator().withMax(0));//  "Max less then or equal to 0 shouldn't be allowed");
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.intGenerator().withMax(-1));//  "Max less then or equal to 0 shouldn't be allowed");
    }

    @Test
    //@DisplayName("Test using withMinAndMax (Positive Ranges)")
    public void testWithMinAndMaxPositiveRanges(){
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(0, 1), 10000), 0, 1);
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(0, 10), 10000), 0, 10);
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(0, 999), 10000), 0, 999);
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(0, Integer.MAX_VALUE - 1), 10000), 0, Integer.MAX_VALUE - 1);
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(1, 2), 10000), 1, 2);
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(100, 110), 10000), 100, 110);
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(999, 1020), 10000), 999, 1020);
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(10000, Integer.MAX_VALUE - 1), 10000), 10000, Integer.MAX_VALUE - 1);
    }

    @Test
    //@DisplayName("Test using withMinAndMax (Negative to Positive Ranges)")
    public void testWithMinAndMaxNegativeToPositiveRanges(){
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(-1, 1), 10000), -1, 1);
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(-10, 10), 10000), -10, 10);
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(-100, 0), 10000), -100, 0);
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(Integer.MIN_VALUE, Integer.MAX_VALUE - 1), 10000), Integer.MIN_VALUE, Integer.MAX_VALUE - 1);
    }

    @Test
    //@DisplayName("Test using withMinAndMax (Negative Ranges)")
    public void testWithMinAndMaxNegativeRanges(){
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(-10, -5), 10000), -10, -5);
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(-1000, -999), 10000), -1000, -999);
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(-100, -50), 10000), -100, -50);
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(Integer.MIN_VALUE, Integer.MIN_VALUE + 50), 10000), Integer.MIN_VALUE, Integer.MIN_VALUE + 50);
    }

    @Test
    //@DisplayName("Test using withMinAndMax with invalid arguments")
    public void testWithMinAndMaxInvalidArguments(){
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.intGenerator().withMinAndMax(0, Integer.MAX_VALUE));// "Max >= Integer.MAX_VALUE shouldn't be allowed");
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.intGenerator().withMinAndMax(0, Integer.MIN_VALUE));// "Max <= 0 shouldn't be allowed");
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.intGenerator().withMinAndMax(0, 0));// "Max <= 0 shouldn't be allowed");
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.intGenerator().withMinAndMax(0, -1));// "Max <= 0 shouldn't be allowed");
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.intGenerator().withMinAndMax(10, 5));// "Max < Min shouldn't be allowed");
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.intGenerator().withMinAndMax(-5, -10));// "Max < Min shouldn't be allowed");
    }

    @Test
    //@DisplayName("Test using ignore with positive numbers")
    public void testIgnorePositive(){
        // single number
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(0, 10).ignore(Arrays.asList(5)), 10000), 0, 10, Arrays.asList(5));
        // single number - origin
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(0, 10).ignore(Arrays.asList(0)), 10000), 0, 10, Arrays.asList(0));
        // single number - bound
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(0, 10).ignore(Arrays.asList(10)), 10000), 0, 10, Arrays.asList(10));
        // first half of numbers except the bound
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(0, 10).ignore(Arrays.asList(1,2,3,4,5)), 10000), 0, 10, Arrays.asList(1,2,3,4,5));
        // first half of numbers
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(0, 10).ignore(Arrays.asList(0,1,2,3,4,5)), 10000), 0, 10, Arrays.asList(0,1,2,3,4,5));
        // spaced out numbers
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(0, 10).ignore(Arrays.asList(0,2,4,6)), 10000), 0, 10, Arrays.asList(0,2,4,6));
        // all numbers except one
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(0, 10).ignore(Arrays.asList(0,1,2,3,4,6,7,8,9,10)), 10000), 0, 10, Arrays.asList(0,1,2,3,4,6,7,8,9,10));
        // numbers outside of range
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(0, 10).ignore(Arrays.asList(11,12,13,14,15)), 10000), 0, 10, Arrays.asList(11,12,13,14,15));
        // first half of numbers - int array
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(0, 10).ignore(0,1,2,3,4,5), 10000), 0, 10, Arrays.asList(0,1,2,3,4,5));
        // duplicate numbers
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(0, 5).ignore(5,5,5,5,5,5,5,5,5,5,5,5), 10000), 0, 10, Arrays.asList(5,5,5,5,5,5,5,5,5,5,5,5));
        // no bounds
        testAllNumbersIgnore(buildRandomList(sergent.intGenerator().ignore(Arrays.asList(31909859)), 10000), Arrays.asList(31909859));
        // null list
        List<Integer> nullList = null;
        testAllNumbersIgnore(buildRandomList(sergent.intGenerator().ignore(nullList), 10000), Arrays.asList());
    }


    @Test
    //@DisplayName("Test using ignore with negative and positive numbers")
    public void testIgnoreNegativePositive(){
        // single number
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(0)), 10000), -10, 10, Arrays.asList(0));
        // single number
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(-5)), 10000), -10, 10, Arrays.asList(-5));
        // single number - origin
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(-10)), 10000), -10, 10, Arrays.asList(-10));
        // single number - bound
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(10)), 10000), -10, 10, Arrays.asList(10));
        // first half of numbers except the bound
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(-9,-8,-7,-6,-5)), 10000), -10, 10, Arrays.asList(-9,-8,-7,-6,-5));
        // first half of numbers
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(-10,-9,-8,-7,-6,-5)), 10000), -10, 10, Arrays.asList(-10,-9,-8,-7,-6,-5));
        // spaced out numbers
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(-10,-8,-6,-4)), 10000), -10, 10, Arrays.asList(-10,-8,-6,-4));
        // all numbers except one
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(-10,-9,-8,-7,-6,-5,-3,-2,-1,0,1,2,3,4,5,6,7,8,9,10)), 10000), -10, 10, Arrays.asList(-10,-9,-8,-7,-6,-5,-3,-2,-1,0,1,2,3,4,5,6,7,8,9,10));
        // numbers outside of range
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(-11,-12,-13,-14,-15)), 10000), -10, 10, Arrays.asList(-11,-12,-13,-14,-15));
        // mixture
        testAllNumbersInRange(buildRandomList(sergent.intGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(-10,-7,-6,-5,-1,0,1,4,5,6,9)), 10000), -10, 10, Arrays.asList(-10,-7,-6,-5,-1,0,1,4,5,6,9));
    }

    private void testAllNumbersInRange(List<Integer> list, int min, int max){
        Assert.assertFalse("All numbers should be between "+min+" and "+max+" inclusive",
                list.stream().anyMatch(r -> r < min || r > max));
    }

    private void testAllNumbersInRange(List<Integer> list, int min, int max, List<Integer> ignore){
        Assert.assertFalse(
                "All numbers should be between "+min+" and "+max+" inclusive and not " + ignore + ", list="+list,
                list.stream().anyMatch(r -> r < min || r > max || ignore.contains(r)));
    }

    private void testAllNumbersIgnore(List<Integer> list, List<Integer> ignore){
        Assert.assertFalse(
                "All numbers should be not be " + ignore,
                list.stream().anyMatch(ignore::contains));
    }

    private List<Integer> buildRandomList(IntGenerator generator, int count){
        ArrayList<Integer> list = new ArrayList<>(count);
        for(int i = 0; i < count; i++){
            list.add(generator.randInt());
        }
        return list;
    }

}