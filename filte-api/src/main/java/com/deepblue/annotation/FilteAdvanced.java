package com.deepblue.annotation;

import com.deepblue.enums.ChannelEnum;
import com.deepblue.enums.SiteIdEnum;
import java.lang.annotation.*;


/**
 * 高级组合过滤 可以供所有 触达方式使用! 拦截器自己定义; 等价于下面所有 注解 一块使用
 * @FilteWhite(openFlag = ture, channel = ChannelEnum.SMS, siteId = SiteIdEnum.BP, paramName  = "items")
 * @FilteBlack(openFlag = ture, channel = ChannelEnum.SMS, siteId = SiteIdEnum.BP, paramName  = "items")
 * @FilteWelth(openFlag = ture, channel = ChannelEnum.SMS, siteId = SiteIdEnum.BP, paramName  = "items")
 * @FilteStop (openFlag = ture, channel = ChannelEnum.SMS, siteId = SiteIdEnum.BP, paramName  = "items")
 * @FiltePause(openFlag = ture, channel = ChannelEnum.SMS, siteId = SiteIdEnum.BP, paramName  = "items")
 * @FilteCheck(openFlag = ture, channel = ChannelEnum.SMS, siteId = SiteIdEnum.BP, paramName  = "items")
 * @FilteSupp (openFlag = ture, channel = ChannelEnum.SMS, siteId = SiteIdEnum.BP, paramName  = "items")
 * @FilteProvi(openFlag = ture, channel = ChannelEnum.SMS, siteId = SiteIdEnum.BP, paramNames = {"items", "isp"})
 * @FilteDupli(openFlag = ture, channel = ChannelEnum.SMS, siteId = SiteIdEnum.BP, paramNames = "items")
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface FilteAdvanced {

    // 渠道类型
    ChannelEnum channel() default ChannelEnum.SMS;

    // 产品线
    SiteIdEnum siteId() default SiteIdEnum.BP;

    // 过滤参数名
    String[] paramNames() default {"items", "isp"};

    // 过滤规则
    FilteRule white() default @FilteRule(openFlag = true);
    FilteRule supp()  default @FilteRule(openFlag = true);
    FilteRule black() default @FilteRule(openFlag = true);
    FilteRule welth() default @FilteRule(openFlag = true);
    FilteRule stop()  default @FilteRule(openFlag = true);
    FilteRule pause() default @FilteRule(openFlag = true);
    FilteRule check() default @FilteRule(openFlag = true);
    FilteRule provi() default @FilteRule(openFlag = true);
    FilteRule dupli() default @FilteRule(openFlag = true);

}
