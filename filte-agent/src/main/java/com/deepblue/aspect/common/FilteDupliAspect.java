package com.deepblue.aspect.common;

import com.deepblue.annotation.common.FilteDupli;
import com.deepblue.enums.ChannelEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

import java.util.UUID;

@Slf4j
@Aspect
@Order(60)
public class FilteDupliAspect {

    @Around("com.deepblue.pointcut.CommonPointcut.filteDupli() && @annotation(filteDupli)")
    public Object around(ProceedingJoinPoint point, FilteDupli filteDupli) throws Throwable {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        boolean openFlag = filteDupli.openFlag();
        String paramName = filteDupli.paramName();
        ChannelEnum channel = filteDupli.channel();

        log.info("FilteDupliAspect uuid: {}, channel: {}, openFlag: {}, paramName: {}",
                uuid, channel, openFlag, paramName);

        return point.proceed();
    }

}
