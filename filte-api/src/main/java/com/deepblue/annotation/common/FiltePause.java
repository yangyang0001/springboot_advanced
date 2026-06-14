package com.deepblue.annotation.common;

import com.deepblue.enums.ChannelEnum;
import com.deepblue.enums.SiteIdEnum;


import java.lang.annotation.*;

/**
 * 过滤 暂停
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface FiltePause {

    // 是否开启过滤
    boolean openFlag() default true;

    // 渠道类型
    ChannelEnum channel() default ChannelEnum.SMS;

    // 产品线
    SiteIdEnum siteId() default SiteIdEnum.BP;

    // 过滤参数名
    String paramName() default "items";

}
