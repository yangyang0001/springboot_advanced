package com.deepblue.aspect.common;

import com.deepblue.annotation.common.FilteSupp;
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
@Order(46)
public class FilteSuppAspect {

    @Around("com.deepblue.pointcut.CommonPointcut.filteSupp() && @annotation(filteSupp)")
    public Object around(ProceedingJoinPoint point, FilteSupp filteSupp) throws Throwable {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        long begin = System.currentTimeMillis();
        log.info("FilteSuppAspect start, uuid: {}", uuid);

        ChannelEnum channel = filteSupp.channel();
        boolean openFlag = filteSupp.openFlag();
        SiteIdEnum siteId = filteSupp.siteId();
        String paramName = filteSupp.paramName();

        log.info("FilteSuppAspect channel: {}, openFlag: {}, siteId: {}, paramName: {}",
                channel, openFlag, siteId, paramName);

        try {
            return point.proceed();
        } finally {
            log.info("FilteSuppAspect end, uuid: {}, cost: {}ms", uuid, System.currentTimeMillis() - begin);
        }
    }

}
