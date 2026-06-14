package com.deepblue.aspect;

import com.alibaba.fastjson.JSON;
import com.deepblue.annotation.FilteAdvanced;
import com.deepblue.annotation.FilteRule;
import com.deepblue.constants.Constants;
import com.deepblue.enums.ChannelEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;

import java.util.UUID;

/**
 * @Order 自定义排序如下
 * FilteAdvancedAspect  5       组合注解
 * FilteWhiteAspect     10
 * FilteBlackAspect     20
 * FilteWelthAspect     25
 * FilteStopAspect      30
 * FiltePauseAspect     40
 * FilteCheckAspect     45
 * FilteSuppAspect      46
 * FilteProviAspect     50
 * FilteDupliAspect     60
 * RateLimiteAspect     70
 * FilteResulAspect     80
 *
 * JVM 参数覆盖规则开关（优先级高于注解 openFlag）:
 *   -Dfilte.white.enabled=false
 *   -Dfilte.black.enabled=false
 *   -Dfilte.welth.enabled=false
 *   -Dfilte.stop.enabled=false
 *   -Dfilte.pause.enabled=false
 *   -Dfilte.check.enabled=false
 *   -Dfilte.supp.enabled=false
 *   -Dfilte.provi.enabled=false
 *   -Dfilte.dupli.enabled=false
 */
@Slf4j
@Aspect
@Order(5)
public class FilteAdvancedAspect {

    private static final String JVM_PROP_PREFIX = "filte.";
    private static final String JVM_PROP_SUFFIX = ".enabled";

    /**
     * 读取 JVM 参数覆盖 openFlag；未设置时沿用注解默认值
     * 示例：-Dfilte.white.enabled=false
     */
    private boolean isEnabled(FilteRule rule, String ruleName) {
        String prop = System.getProperty(JVM_PROP_PREFIX + ruleName + JVM_PROP_SUFFIX);
        return prop != null ? Boolean.parseBoolean(prop) : rule.openFlag();
    }

    @Around("com.deepblue.pointcut.CommonPointcut.filteAdvanced() && @annotation(filteAdvanced)")
    public Object around(ProceedingJoinPoint point, FilteAdvanced filteAdvanced) throws Throwable {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        long begin = System.currentTimeMillis();
        MDC.put(Constants.UU_ID, uuid);
        Object[] args = point.getArgs();

        log.info("FilteAdvancedAspect invoke start, uuid: {}, args: {}", uuid, JSON.toJSONString(args));

        ChannelEnum channel = filteAdvanced.channel();

        // 读取过滤规则（JVM 参数可覆盖 openFlag）
        boolean whiteFlag = isEnabled(filteAdvanced.white(), "white");
        boolean blackFlag = isEnabled(filteAdvanced.black(), "black");
        boolean welthFlag = isEnabled(filteAdvanced.welth(), "welth");
        boolean stopFlag  = isEnabled(filteAdvanced.stop(),  "stop");
        boolean pauseFlag = isEnabled(filteAdvanced.pause(), "pause");
        boolean checkFlag = isEnabled(filteAdvanced.check(), "check");
        boolean suppFlag  = isEnabled(filteAdvanced.supp(),  "supp");
        boolean proviFlag = isEnabled(filteAdvanced.provi(), "provi");
        boolean dupliFlag = isEnabled(filteAdvanced.dupli(), "dupli");

        log.info("FilteAdvancedAspect invoke success channel: {}, whiteFlag: {}, blackFlag: {}, welthFlag: {}, stopFlag: {}, pauseFlag: {}, checkFlag: {}, suppFlag: {}, proviFlag: {}, dupliFlag: {}",
                channel, whiteFlag, blackFlag, welthFlag, stopFlag, pauseFlag, checkFlag, suppFlag, proviFlag, dupliFlag);

        Object result = null;
        try {
            result = point.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            MDC.remove(Constants.UU_ID);
            log.info("FilteAdvancedAspect invoke end uuid: {}, cost time: {}", uuid, System.currentTimeMillis() - begin);
        }

        return result;
    }

}
