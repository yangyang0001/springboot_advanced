package com.deepblue.aspect.common;

import com.deepblue.annotation.common.FilteResul;
import com.deepblue.enums.ChannelEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

import java.util.UUID;

@Slf4j
@Aspect
@Order(80)
public class FilteResulAspect {

    @Around("com.deepblue.pointcut.CommonPointcut.filteResul() && @annotation(filteResul)")
    public Object around(ProceedingJoinPoint point, FilteResul filteResul) throws Throwable {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        boolean openFlag = filteResul.openFlag();
        ChannelEnum channel = filteResul.channel();
        String paramName = filteResul.paramName();

        log.info("FilteResulAspect uuid: {}, channel: {}, openFlag: {}, paramName: {}",
                uuid, channel, openFlag, paramName);

        try {
            return point.proceed();
        } catch (Throwable e) {
            log.error("FilteResulAspect caught exception, uuid: {}, channel: {}, paramName: {}, error: {}",
                    uuid, channel.getName(), paramName, e.getMessage());
            throw e;
        }
    }

}
