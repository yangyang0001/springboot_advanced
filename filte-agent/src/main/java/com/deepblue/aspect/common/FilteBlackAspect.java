package com.deepblue.aspect.common;

import com.deepblue.annotation.common.FilteBlack;
import com.deepblue.enums.ChannelEnum;
import com.deepblue.enums.SiteIdEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
@Order(20)
public class FilteBlackAspect {

    @Around("com.deepblue.pointcut.CommonPointcut.filteBlack() && @annotation(filteBlack)")
    public Object around(ProceedingJoinPoint point, FilteBlack filteBlack) throws Throwable {
        String paramName = filteBlack.paramName();
        ChannelEnum channel = filteBlack.channel();
        SiteIdEnum siteId = filteBlack.siteId();

        log.info("FilteBlackAspect channel: {}, siteId: {}, paramName: {}", channel, siteId, paramName);

        return point.proceed();
    }

}
