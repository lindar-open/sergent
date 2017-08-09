package com.lindar.sergent;

import org.apache.commons.rng.UniformRandomProvider;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.function.IntSupplier;
import java.util.function.LongSupplier;

@Aspect
@Component
public class RandomProviderOverseer {

    @Pointcut("(execution(* org.apache.commons.rng.UniformRandomProvider.nextInt(int)) && target(randomProvider)) || " +
            "(execution(* org.apache.commons.rng.UniformRandomProvider.nextInt()) && target(randomProvider)) || " +
            "(execution(* org.apache.commons.rng.UniformRandomProvider.nextLong()) && target(randomProvider)) || " +
            "(execution(* org.apache.commons.rng.UniformRandomProvider.nextLong(long)) && target(randomProvider))")
    private void callNext(UniformRandomProvider randomProvider){}

    @Before(value = "callNext(randomProvider)", argNames = "randomProvider")
    public void beforeCallNext(UniformRandomProvider randomProvider) {
        RandomProviderFactory.incrementAccess();
    }

    @Pointcut("execution(* com.lindar.sergent.IntGenerator.randInt())")
    private void randInt() {}

    @Around(value = "randInt()", argNames = "pjp")
    public Object aroundRandInt(ProceedingJoinPoint pjp) throws Throwable {
        IntGenerator intGenerator = (IntGenerator) pjp.getTarget();

        SergentConfigs sergentConfigs = SergentConfigs.INSTANCE;
        if (sergentConfigs.isBackgroundCyclingEnabled()) {
            UniformRandomProvider randomProvider = RandomProviderFactory.getInstance();

            int howManyCallsToSkip = randomProvider.nextInt((sergentConfigs.getBackgroundCyclingMaxSkipCounter() - sergentConfigs.getBackgroundCyclingMinSkipCounter()) + 1)
                    + sergentConfigs.getBackgroundCyclingMinSkipCounter();

            int min = intGenerator.getMin();
            int max = intGenerator.getMax();

            IntSupplier randomValueSupplier;
            if (min > 0 && max > 0) {
                randomValueSupplier = () -> (randomProvider.nextInt((max - min) + 1) + min);
            } else if (max > 0) {
                randomValueSupplier = () -> randomProvider.nextInt(max);
            } else {
                randomValueSupplier = randomProvider::nextInt;
            }

            int randInt = randomValueSupplier.getAsInt();
            for (int i = 0; i < howManyCallsToSkip; i++) {
                randInt = randomValueSupplier.getAsInt();
            }
            return randInt;
        }
        System.out.println("Background cycling is disabled. Rock on and return original value");
        return pjp.proceed(new Object[] {intGenerator});
    }


    @Pointcut("execution(* com.lindar.sergent.LongGenerator.randLong())")
    private void randLong() {}

    @Around(value = "randLong()", argNames = "pjp")
    public Object aroundRandLong(ProceedingJoinPoint pjp) throws Throwable {
        LongGenerator longGenerator = (LongGenerator) pjp.getTarget();

        SergentConfigs sergentConfigs = SergentConfigs.INSTANCE;
        if (sergentConfigs.isBackgroundCyclingEnabled()) {
            UniformRandomProvider randomProvider = RandomProviderFactory.getInstance();

            int howManyCallsToSkip = randomProvider.nextInt((sergentConfigs.getBackgroundCyclingMaxSkipCounter() - sergentConfigs.getBackgroundCyclingMinSkipCounter()) + 1)
                    + sergentConfigs.getBackgroundCyclingMinSkipCounter();

            long min = longGenerator.getMin();
            long max = longGenerator.getMax();

            LongSupplier randomValueSupplier;
            if (min > 0 && max > 0) {
                randomValueSupplier = () -> (randomProvider.nextLong((max - min) + 1) + min);
            } else if (max > 0) {
                randomValueSupplier = () -> randomProvider.nextLong(max);
            } else {
                randomValueSupplier = randomProvider::nextLong;
            }

            long randLong = randomValueSupplier.getAsLong();
            for (int i = 0; i < howManyCallsToSkip; i++) {
                randLong = randomValueSupplier.getAsLong();
            }
            return randLong;
        }
        System.out.println("Background cycling is disabled. Rock on and return original value");
        return pjp.proceed(new Object[] {longGenerator});
    }
}