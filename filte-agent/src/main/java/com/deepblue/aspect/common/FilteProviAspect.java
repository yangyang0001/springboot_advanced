package com.deepblue.aspect.common;

import com.deepblue.annotation.common.FilteProvi;
import com.deepblue.enums.ChannelEnum;
import com.deepblue.enums.SiteIdEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
@Order(50)
public class FilteProviAspect {

    @Around("com.deepblue.pointcut.CommonPointcut.filteProvi() && @annotation(filteProvi)")
    public Object around(ProceedingJoinPoint point, FilteProvi filteProvi) throws Throwable {
        boolean openFlag = filteProvi.openFlag();

        if (openFlag) {
            ChannelEnum channel = filteProvi.channel();
            SiteIdEnum siteId = filteProvi.siteId();

            String[] configured = filteProvi.paramNames();
            String paramName1 = configured.length > 0 ? configured[0].trim() : "";
            String paramName2 = configured.length > 1 ? configured[1].trim() : "";

            log.info("FilteProviAspect channel: {}, siteId: {}, paramName1: {}, paramName2: {}",
                    channel, siteId, paramName1, paramName2);
        }

        return point.proceed();
    }

}
