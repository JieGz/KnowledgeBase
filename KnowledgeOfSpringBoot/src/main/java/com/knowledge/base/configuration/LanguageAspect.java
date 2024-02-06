package com.knowledge.base.configuration;

import com.knowledge.base.response.ResultData;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jieguangzhi
 * @date 2024-01-30
 */
@Aspect
@Component
@AllArgsConstructor
@ConditionalOnProperty(prefix = "lang", name = "open", havingValue = "true")
public class LanguageAspect {
    @Autowired
    I18nUtils i18nUtils;

    @Pointcut("execution(* com.knowledge.base.controller.*.*(..)))")
    public void annotationLangCut() {
    }

    /**
     * 拦截controller层返回的结果，修改msg字段
     *
     * @param point
     * @param obj
     */
    @AfterReturning(pointcut = "annotationLangCut()", returning = "obj")
    public void around(JoinPoint point, Object obj) {
        Object resultObject = obj;
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            //从获取RequestAttributes中获取HttpServletRequest的信息
            ResultData r = (ResultData) obj;
            String msg = r.getMessage().trim();
            r.setMessage(i18nUtils.getKey(msg));
        } catch (Exception e) {
            e.printStackTrace();
            //返回原值
            obj = resultObject;
        }
    }
}
