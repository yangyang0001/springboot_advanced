package com.deepblue.annotation.common;

import com.deepblue.enums.ChannelEnum;
import com.deepblue.enums.SiteIdEnum;


import java.lang.annotation.*;

/**
 * 后置增强
 * 1、对发送结果进行处理, 发送失败的数据, 从redis中移除
 * 2、对发送结果 添加监控
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface FilteResul {

    // 是否开启过滤
    boolean openFlag() default true;

    // 渠道类型
    ChannelEnum channel() default ChannelEnum.SMS;

    // 产品线
    SiteIdEnum siteId() default SiteIdEnum.BP;

    // 参数名
    String paramName() default "smsAccount";

}
