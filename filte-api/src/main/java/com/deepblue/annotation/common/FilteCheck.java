package com.deepblue.annotation.common;

import com.deepblue.enums.ChannelEnum;
import com.deepblue.enums.SiteIdEnum;


import java.lang.annotation.*;

/**
 * 校验短信内容 目前只有营销短信 使用
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface FilteCheck {
    // 是否开启过滤
    boolean openFlag() default true;

    // 渠道类型
    ChannelEnum channel() default ChannelEnum.SMS;

    // 产品线
    SiteIdEnum siteId() default SiteIdEnum.BP;

    // 过滤参数名
    String paramName() default "items";
}
