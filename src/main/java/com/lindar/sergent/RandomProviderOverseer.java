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

//    @Pointcut("(execution(* org.apache.commons.rng.UniformRandomProvider.nextInt(int))) || " +
//            "(execution(* org.apache.commons.rng.UniformRandomProvider.nextInt())) || " +
//            "(execution(* org.apache.commons.rng.UniformRandomProvider.nextLong())) || " +
//            "(execution(* org.apache.commons.rng.UniformRandomProvider.nextLong(long)))")
//    private void callNext(){}
//
//    @Before(value = "callNext()", argNames = "joinPoint")
//    public void beforeCallNext(JoinPoint joinPoint) {
//        RandomProviderFactory.incrementAccess();
//    }

    @Pointcut("execution(* com.lindar.sergent.IntGenerator.randInt())")
    private void randInt() {}

    @Around(value = "randInt()", argNames = "pjp")
    public Object aroundRandInt(ProceedingJoinPoint pjp) throws Throwable {
        IntGenerator intGenerator = (IntGenerator) pjp.getTarget();

        SergentConfigs sergentConfigs = SergentConfigs.INSTANCE;
        if (sergentConfigs.isBackgroundCyclingEnabled()) {
            UniformRandomProvider randomProvider = RandomProviderFactory.getInstance(intGenerator.randomProviderId);

            int howManyCallsToSkip = randomProvider.nextInt((sergentConfigs.getBackgroundCyclingMaxSkipCounter() - sergentConfigs.getBackgroundCyclingMinSkipCounter()) + 1)
                    + sergentConfigs.getBackgroundCyclingMinSkipCounter();

            int randInt = (int)pjp.proceed();
            for (int i = 0; i < howManyCallsToSkip; i++) {
                randInt = (int)pjp.proceed();
                RandomProviderFactory.incrementAccess(intGenerator.randomProviderId);
            }
            return randInt;
        }
        RandomProviderFactory.incrementAccess(intGenerator.randomProviderId);
        return pjp.proceed(new Object[] {intGenerator});
    }


    @Pointcut("execution(* com.lindar.sergent.LongGenerator.randLong())")
    private void randLong() {}

    @Around(value = "randLong()", argNames = "pjp")
    public Object aroundRandLong(ProceedingJoinPoint pjp) throws Throwable {
        LongGenerator longGenerator = (LongGenerator) pjp.getTarget();

        SergentConfigs sergentConfigs = SergentConfigs.INSTANCE;
        if (sergentConfigs.isBackgroundCyclingEnabled()) {
            UniformRandomProvider randomProvider = RandomProviderFactory.getInstance(longGenerator.randomProviderId);

            int howManyCallsToSkip = randomProvider.nextInt((sergentConfigs.getBackgroundCyclingMaxSkipCounter() - sergentConfigs.getBackgroundCyclingMinSkipCounter()) + 1)
                    + sergentConfigs.getBackgroundCyclingMinSkipCounter();

            long randLong = (long)pjp.proceed();
            for (int i = 0; i < howManyCallsToSkip; i++) {
                randLong = (long)pjp.proceed();
                RandomProviderFactory.incrementAccess(longGenerator.randomProviderId);
            }
            return randLong;
        }
        RandomProviderFactory.incrementAccess(longGenerator.randomProviderId);
        return pjp.proceed(new Object[] {longGenerator});
    }
}
