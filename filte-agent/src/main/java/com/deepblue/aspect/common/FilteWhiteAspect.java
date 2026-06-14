package com.deepblue.aspect.common;

import com.deepblue.annotation.common.FilteWhite;
import com.deepblue.enums.ChannelEnum;
import com.deepblue.enums.SiteIdEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

import java.util.UUID;

@Slf4j
@Aspect
@Order(10)
public class FilteWhiteAspect {

    @Around("com.deepblue.pointcut.CommonPointcut.filteWhite() && @annotation(filteWhite)")
    public Object around(ProceedingJoinPoint point, FilteWhite filteWhite) throws Throwable {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        long begin = System.currentTimeMillis();
        log.info("FilteWhiteAspect start, uuid: {}", uuid);

        ChannelEnum channel = filteWhite.channel();
        boolean openFlag = filteWhite.openFlag();
        SiteIdEnum siteId = filteWhite.siteId();
        String paramName = filteWhite.paramName();

        log.info("FilteWhiteAspect channel: {}, openFlag: {}, siteId: {}, paramName: {}",
                channel, openFlag, siteId, paramName);

        try {
            return point.proceed();
        } finally {
            log.info("FilteWhiteAspect end, uuid: {}, cost: {}ms", uuid, System.currentTimeMillis() - begin);
        }
    }

}
