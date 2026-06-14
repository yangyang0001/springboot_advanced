package com.deepblue.aspect.common;

import com.deepblue.annotation.common.FiltePause;
import com.deepblue.enums.ChannelEnum;
import com.deepblue.enums.SiteIdEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
@Order(40)
public class FiltePauseAspect {

    @Around("com.deepblue.pointcut.CommonPointcut.filtePause() && @annotation(filtePause)")
    public Object around(ProceedingJoinPoint point, FiltePause filtePause) throws Throwable {
        boolean openFlag = filtePause.openFlag();
        String paramName = filtePause.paramName();
        ChannelEnum channel = filtePause.channel();
        SiteIdEnum siteId = filtePause.siteId();

        log.info("FiltePauseAspect channel: {}, openFlag: {}, siteId: {}, paramName: {}",
                channel, openFlag, siteId, paramName);

        return point.proceed();
    }

}
