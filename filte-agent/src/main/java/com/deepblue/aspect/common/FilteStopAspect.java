package com.deepblue.aspect.common;

import com.deepblue.annotation.common.FilteStop;
import com.deepblue.enums.ChannelEnum;
import com.deepblue.enums.SiteIdEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
@Order(30)
public class FilteStopAspect {

    @Around("com.deepblue.pointcut.CommonPointcut.filteStop() && @annotation(filteStop)")
    public Object around(ProceedingJoinPoint point, FilteStop filteStop) throws Throwable {
        boolean openFlag = filteStop.openFlag();
        String paramName = filteStop.paramName();
        ChannelEnum channel = filteStop.channel();
        SiteIdEnum siteId = filteStop.siteId();

        log.info("FilteStopAspect channel: {}, openFlag: {}, siteId: {}, paramName: {}",
                channel, openFlag, siteId, paramName);

        return point.proceed();
    }

}
