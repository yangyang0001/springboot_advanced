package com.deepblue.aspect.common;

import com.deepblue.annotation.common.FilteCheck;
import com.deepblue.enums.ChannelEnum;
import com.deepblue.enums.SiteIdEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
@Order(45)
public class FilteCheckAspect {

    @Around("com.deepblue.pointcut.CommonPointcut.filteCheck() && @annotation(filteCheck)")
    public Object around(ProceedingJoinPoint point, FilteCheck filteCheck) throws Throwable {
        boolean openFlag = filteCheck.openFlag();
        String paramName = filteCheck.paramName();
        ChannelEnum channel = filteCheck.channel();
        SiteIdEnum siteId = filteCheck.siteId();

        log.info("FilteCheckAspect channel: {}, openFlag: {}, siteId: {}, paramName: {}",
                channel, openFlag, siteId, paramName);

        return point.proceed();
    }

}
