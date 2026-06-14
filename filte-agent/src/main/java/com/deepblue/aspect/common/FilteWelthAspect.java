package com.deepblue.aspect.common;

import com.deepblue.annotation.common.FilteWelth;
import com.deepblue.enums.ChannelEnum;
import com.deepblue.enums.SiteIdEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
@Order(25)
public class FilteWelthAspect {

    @Around("com.deepblue.pointcut.CommonPointcut.filteWelth() && @annotation(filteWelth)")
    public Object around(ProceedingJoinPoint point, FilteWelth filteWelth) throws Throwable {
        boolean openFlag = filteWelth.openFlag();
        String paramName = filteWelth.paramName();
        ChannelEnum channel = filteWelth.channel();
        SiteIdEnum siteId = filteWelth.siteId();

        log.info("FilteWelthAspect channel: {}, openFlag: {}, siteId: {}, paramName: {}",
                channel, openFlag, siteId, paramName);

        return point.proceed();
    }

}
