package com.deepblue.aspect.common;

import com.deepblue.annotation.common.RateLimite;
import com.deepblue.enums.ChannelEnum;
import com.deepblue.enums.LimitEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
@Order(70)
public class RateLimiteAspect {

    @Around("com.deepblue.pointcut.CommonPointcut.rateLimite() && @annotation(rateLimite)")
    public Object around(ProceedingJoinPoint point, RateLimite rateLimite) throws Throwable {
        boolean openFlag = rateLimite.openFlag();
        ChannelEnum channel = rateLimite.channel();
        LimitEnum limitEnum = rateLimite.limite();

        log.info("RateLimiteAspect channel: {}, openFlag: {}, limitEnum: {}",
                channel, openFlag, limitEnum);

        return point.proceed();
    }

}
