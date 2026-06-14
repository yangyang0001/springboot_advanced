package com.deepblue.config;

import com.deepblue.aspect.FilteAdvancedAspect;
import com.deepblue.aspect.common.*;
import com.deepblue.pointcut.CommonPointcut;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@AutoConfiguration
@EnableAspectJAutoProxy
public class FilteAdvancedAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CommonPointcut commonPointcut() {
        return new CommonPointcut();
    }

    // ---- 组合切面 ----

    @Bean
    @ConditionalOnMissingBean
    public FilteAdvancedAspect filteAdvancedAspect() {
        return new FilteAdvancedAspect();
    }

    // ---- 独立切面（Order 10~80）----

    @Bean
    @ConditionalOnMissingBean
    public FilteWhiteAspect filteWhiteAspect() {
        return new FilteWhiteAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public FilteBlackAspect filteBlackAspect() {
        return new FilteBlackAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public FilteWelthAspect filteWelthAspect() {
        return new FilteWelthAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public FilteStopAspect filteStopAspect() {
        return new FilteStopAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public FiltePauseAspect filtePauseAspect() {
        return new FiltePauseAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public FilteCheckAspect filteCheckAspect() {
        return new FilteCheckAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public FilteSuppAspect filteSuppAspect() {
        return new FilteSuppAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public FilteProviAspect filteProviAspect() {
        return new FilteProviAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public FilteDupliAspect filteDupliAspect() {
        return new FilteDupliAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public RateLimiteAspect rateLimiteAspect() {
        return new RateLimiteAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public FilteResulAspect filteResulAspect() {
        return new FilteResulAspect();
    }

}
