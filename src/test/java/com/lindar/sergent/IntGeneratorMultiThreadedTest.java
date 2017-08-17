package com.lindar.sergent;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.stream.IntStream;

@RunWith(ConcurrentTestRunner.class)
public class IntGeneratorMultiThreadedTest {
    private static final int SIZE_CHECK = 20_001;
    private final static int THREAD_COUNT = 4;

    @Before
    public void setUp() {
    }

    @Test
    @ThreadCount(THREAD_COUNT)
    public void randMultiThreadedSingleInt() {
        Sergent sergent = SergentFactory.newInstance();
        System.out.println("Running thread : " + sergent.intGenerator().randInt() + " with provider ID: " + sergent.randomProviderSeed);
        IntStream.rangeClosed(0, SIZE_CHECK).forEach(i -> {
            Integer number = sergent.intGenerator().randInt();
            Assert.assertNotNull("Failed to generate random number", number);
        });
    }

}