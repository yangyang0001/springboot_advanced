package com.deepblue.pointcut;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
* @Order 自定义排序如下
* FilteAdvancedAspect	5       组合注解
*
* FilteWhiteAspect	    10
* FilteBlackAspect 	20
* FilteWelthAspect	    25
* FilteStopAspect		30
* FiltePauseAspect	    40
* FilteCheckAspect	    45
* FilteSuppAspect	    46
* FilteProviAspect	    50
* FilteDupliAspect	    60
* RateLimiteAspect	    70
* FilteResulAspect	    80
*/
@Aspect
public class CommonPointcut {

   /**
    * 公用注解类, 这块提供的处理器可以公用
    */
   @Pointcut("@annotation(com.deepblue.annotation.common.FilteWhite)")
   public void filteWhite() {}

   @Pointcut("@annotation(com.deepblue.annotation.common.FilteBlack)")
   public void filteBlack() {}

   @Pointcut("@annotation(com.deepblue.annotation.common.FilteWelth)")
   public void filteWelth() {}

   @Pointcut("@annotation(com.deepblue.annotation.common.FilteStop)")
   public void filteStop() {}

   @Pointcut("@annotation(com.deepblue.annotation.common.FiltePause)")
   public void filtePause() {}

   @Pointcut("@annotation(com.deepblue.annotation.common.FilteCheck)")
   public void filteCheck() {}

   @Pointcut("@annotation(com.deepblue.annotation.common.FilteSupp)")
   public void filteSupp() {}

   @Pointcut("@annotation(com.deepblue.annotation.common.FilteProvi)")
   public void filteProvi() {}

   @Pointcut("@annotation(com.deepblue.annotation.common.FilteDupli)")
   public void filteDupli() {}

   @Pointcut("@annotation(com.deepblue.annotation.common.RateLimite)")
   public void rateLimite() {}

   @Pointcut("@annotation(com.deepblue.annotation.common.FilteResul)")
   public void filteResul() {}


   /**
    * 高级注解 或 组合注解
    */
   @Pointcut("@annotation(com.deepblue.annotation.FilteRule)")
   public void filteRule() {}

   @Pointcut("@annotation(com.deepblue.annotation.FilteAdvanced)")
   public void filteAdvanced() {}

}
