package com.lindar.sergent;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LongGeneratorTest {

    private Sergent sergent;

    @Before
    public void setUp() {
        sergent = SergentFactory.newInstance();
    }

    @Test
    //@DisplayName("Test using withMax")
    public void testWithMax(){
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMax(1), 10000), 0, 1);
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMax(10), 10000), 0, 10);
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMax(999), 10000), 0, 999);
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMax(Long.MAX_VALUE - 1), 10000), 0, Long.MAX_VALUE - 1);
    }

    @Test
    //@DisplayName("Test using withMax with invalid arguments")
    public void testWithMaxInvalidArguments(){
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.longGenerator().withMax(Long.MAX_VALUE));//, "Max >= Long.MAX_VALUE shouldn't be allowed");
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.longGenerator().withMax(Long.MIN_VALUE));//, "Max less then 0 shouldn't be allowed");
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.longGenerator().withMax(0));//, "Max less then or equal to 0 shouldn't be allowed");
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.longGenerator().withMax(-1));//, "Max less then or equal to 0 shouldn't be allowed");
    }

    @Test
    //@DisplayName("Test using withMinAndMax (Positive Ranges)")
    public void testWithMinAndMaxPositiveRanges(){
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(0, 1), 10000), 0, 1);
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(0, 10), 10000), 0, 10);
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(0, 999), 10000), 0, 999);
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(0, Long.MAX_VALUE - 1), 10000), 0, Long.MAX_VALUE - 1);
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(1, 2), 10000), 1, 2);
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(100, 110), 10000), 100, 110);
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(999, 1020), 10000), 999, 1020);
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(10000, Long.MAX_VALUE - 1), 10000), 10000, Long.MAX_VALUE - 1);
    }

    @Test
    //@DisplayName("Test using withMinAndMax (Negative to Positive Ranges)")
    public void testWithMinAndMaxNegativeToPositiveRanges(){
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(-1, 1), 10000), -1, 1);
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(-10, 10), 10000), -10, 10);
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(-100, 0), 10000), -100, 0);
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(Long.MIN_VALUE, Long.MAX_VALUE - 1), 10000), Long.MIN_VALUE, Long.MAX_VALUE - 1);
    }

    @Test
    //@DisplayName("Test using withMinAndMax (Negative Ranges)")
    public void testWithMinAndMaxNegativeRanges(){
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(-10, -5), 10000), -10, -5);
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(-1000, -999), 10000), -1000, -999);
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(-100, -50), 10000), -100, -50);
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(Long.MIN_VALUE, Long.MIN_VALUE + 50), 10000), Long.MIN_VALUE, Long.MIN_VALUE + 50);
    }

    @Test
    //@DisplayName("Test using withMinAndMax with invalid arguments")
    public void testWithMinAndMaxInvalidArguments(){
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.longGenerator().withMinAndMax(0, Long.MAX_VALUE));//, "Max >= Long.MAX_VALUE shouldn't be allowed");
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.longGenerator().withMinAndMax(0, Long.MIN_VALUE));//, "Max <= 0 shouldn't be allowed");
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.longGenerator().withMinAndMax(0, 0));//, "Max <= 0 shouldn't be allowed");
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.longGenerator().withMinAndMax(0, -1));//, "Max <= 0 shouldn't be allowed");
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.longGenerator().withMinAndMax(10, 5));//, "Max < Min shouldn't be allowed");
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> sergent.longGenerator().withMinAndMax(-5, -10));//, "Max < Min shouldn't be allowed");
    }

    @Test
    //@DisplayName("Test using ignore with positive numbers")
    public void testIgnorePositive(){
        // single number
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(0, 10).ignore(Arrays.asList(5L)), 10000), 0, 10, Arrays.asList(5L));
        // single number - origin
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(0, 10).ignore(Arrays.asList(0L)), 10000), 0, 10, Arrays.asList(0L));
        // single number - bound
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(0, 10).ignore(Arrays.asList(10L)), 10000), 0, 10, Arrays.asList(10L));
        // first half of numbers except the bound
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(0, 10).ignore(Arrays.asList(1L,2L,3L,4L,5L)), 10000), 0, 10, Arrays.asList(1L,2L,3L,4L,5L));
        // first half of numbers
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(0, 10).ignore(Arrays.asList(0L,1L,2L,3L,4L,5L)), 10000), 0, 10, Arrays.asList(0L,1L,2L,3L,4L,5L));
        // spaced out numbers
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(0, 10).ignore(Arrays.asList(0L,2L,4L,6L)), 10000), 0, 10, Arrays.asList(0L,2L,4L,6L));
        // all numbers except one
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(0, 10).ignore(Arrays.asList(0L,1L,2L,3L,4L,6L,7L,8L,9L,10L)), 10000), 0, 10, Arrays.asList(0L,1L,2L,3L,4L,6L,7L,8L,9L,10L));
        // numbers outside of range
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(0, 10).ignore(Arrays.asList(11L,12L,13L,14L,15L)), 10000), 0, 10, Arrays.asList(11L,12L,13L,14L,15L));
        // first half of numbers - long array
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(0, 10).ignore(0,1,2,3,4,5), 10000), 0, 10, Arrays.asList(0L,1L,2L,3L,4L,5L));
        // no bounds
        testAllNumbersIgnore(buildRandomList(sergent.longGenerator().ignore(Arrays.asList(31909859L)), 10000), Arrays.asList(31909859L));
        // null list
        List<Long> nullList = null;
        testAllNumbersIgnore(buildRandomList(sergent.longGenerator().ignore(nullList), 10000), Arrays.asList());
    }


    @Test
    //@DisplayName("Test using ignore with negative and positive numbers")
    public void testIgnoreNegativePositive(){
        // single number
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(0L)), 10000), -10, 10, Arrays.asList(0L));
        // single number
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(-5L)), 10000), -10, 10, Arrays.asList(-5L));
        // single number - origin
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(-10L)), 10000), -10, 10, Arrays.asList(-10L));
        // single number - bound
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(10L)), 10000), -10, 10, Arrays.asList(10L));
        // first half of numbers except the bound
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(-9L,-8L,-7L,-6L,-5L)), 10000), -10, 10, Arrays.asList(-9L,-8L,-7L,-6L,-5L));
        // first half of numbers
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(-10L,-9L,-8L,-7L,-6L,-5L)), 10000), -10, 10, Arrays.asList(-10L,-9L,-8L,-7L,-6L,-5L));
        // spaced out numbers
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(-10L,-8L,-6L,-4L)), 10000), -10, 10, Arrays.asList(-10L,-8L,-6L,-4L));
        // all numbers except one
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(-10L,-9L,-8L,-7L,-6L,-5L,-3L,-2L,-1L,0L,1L,2L,3L,4L,5L,6L,7L,8L,9L,10L)), 10000), -10, 10, Arrays.asList(-10L,-9L,-8L,-7L,-6L,-5L,-3L,-2L,-1L,0L,1L,2L,3L,4L,5L,6L,7L,8L,9L,10L));
        // numbers outside of range
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(-11L,-12L,-13L,-14L,-15L)), 10000), -10, 10, Arrays.asList(-11L,-12L,-13L,-14L,-15L));
        // mixture
        testAllNumbersInRange(buildRandomList(sergent.longGenerator().withMinAndMax(-10, 10).ignore(Arrays.asList(-10L,-7L,-6L,-5L,-1L,0L,1L,4L,5L,6L,9L)), 10000), -10, 10, Arrays.asList(-10L,-7L,-6L,-5L,-1L,0L,1L,4L,5L,6L,9L));
    }

    private void testAllNumbersInRange(List<Long> list, long min, long max){
        Assert.assertFalse(
                "All numbers should be between "+min+" and "+max+" inclusive",
                list.stream().anyMatch(r -> r < min || r > max));
    }

    private void testAllNumbersInRange(List<Long> list, long min, long max, List<Long> ignore){
        Assert.assertFalse("All numbers should be between "+min+" and "+max+" inclusive and not " + ignore + ", list="+list,
                list.stream().anyMatch(r -> r < min || r > max || ignore.contains(r)));
    }

    private void testAllNumbersIgnore(List<Long> list, List<Long> ignore){
        Assert.assertFalse("All numbers should be not be " + ignore,
                list.stream().anyMatch(ignore::contains));
    }

    private List<Long> buildRandomList(LongGenerator generator, int count){
        ArrayList<Long> list = new ArrayList<Long>(count);
        for(long i = 0; i < count; i++){
            list.add(generator.randLong());
        }
        return list;
    }

}