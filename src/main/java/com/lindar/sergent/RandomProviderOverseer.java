package com.lindar.sergent;

import org.apache.commons.rng.UniformRandomProvider;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RandomProviderOverseer {

    @Pointcut("execution(* com.lindar.sergent.IntGenerator.randInt())")
    private void randInt() {}

    @Around(value = "randInt()", argNames = "pjp")
    public Object aroundRandInt(ProceedingJoinPoint pjp) throws Throwable {
        IntGenerator intGenerator = (IntGenerator) pjp.getTarget();

        SergentConfigs sergentConfigs = SergentConfigs.INSTANCE;
        if (sergentConfigs.isBackgroundCyclingEnabled()) {
            UniformRandomProvider randomProvider = RandomProviderFactory.getInstance(intGenerator.randomProviderSeed);

            int howManyCallsToSkip = randomProvider.nextInt((sergentConfigs.getBackgroundCyclingMaxSkipCounter() - sergentConfigs.getBackgroundCyclingMinSkipCounter()) + 1)
                    + sergentConfigs.getBackgroundCyclingMinSkipCounter();

            int randInt = (int)pjp.proceed();
            for (int i = 0; i < howManyCallsToSkip; i++) {
                randInt = (int)pjp.proceed();
            }
            return randInt;
        }
        return pjp.proceed(new Object[] {intGenerator});
    }


    @Pointcut("execution(* com.lindar.sergent.LongGenerator.randLong())")
    private void randLong() {}

    @Around(value = "randLong()", argNames = "pjp")
    public Object aroundRandLong(ProceedingJoinPoint pjp) throws Throwable {
        LongGenerator longGenerator = (LongGenerator) pjp.getTarget();

        SergentConfigs sergentConfigs = SergentConfigs.INSTANCE;
        if (sergentConfigs.isBackgroundCyclingEnabled()) {
            UniformRandomProvider randomProvider = RandomProviderFactory.getInstance(longGenerator.randomProviderSeed);

            int howManyCallsToSkip = randomProvider.nextInt((sergentConfigs.getBackgroundCyclingMaxSkipCounter() - sergentConfigs.getBackgroundCyclingMinSkipCounter()) + 1)
                    + sergentConfigs.getBackgroundCyclingMinSkipCounter();

            long randLong = (long)pjp.proceed();
            for (int i = 0; i < howManyCallsToSkip; i++) {
                randLong = (long)pjp.proceed();
            }
            return randLong;
        }
        return pjp.proceed(new Object[] {longGenerator});
    }
}
