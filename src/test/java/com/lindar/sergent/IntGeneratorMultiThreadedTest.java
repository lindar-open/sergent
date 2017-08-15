package com.lindar.sergent;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.stream.IntStream;

@RunWith(ConcurrentTestRunner.class)
public class IntGeneratorMultiThreadedTest {
    private static final int SIZE_CHECK = 10_001;
    private final static int THREAD_COUNT = 4;

    @Before
    public void setUp() {
    }

    @Test
    @ThreadCount(THREAD_COUNT)
    public void randMultiThreadedBigIntList() {
        List<Integer> list = SergentFactory.newInstance().listGenerator().ofSize(SIZE_CHECK).randIntegers();
        Assert.assertTrue("Not all numbers have been generated", list.size() == SIZE_CHECK);
    }

    @Test
    @ThreadCount(THREAD_COUNT)
    public void randMultiThreadedSingleInt() {
        System.out.println("Running thread : " + SergentFactory.newInstance().intGenerator().randInt());
        IntStream.rangeClosed(0, SIZE_CHECK).forEach(i -> {
            Integer number = SergentFactory.newInstance().intGenerator().randInt();
            Assert.assertNotNull("Failed to generate random number", number);
        });
    }

}