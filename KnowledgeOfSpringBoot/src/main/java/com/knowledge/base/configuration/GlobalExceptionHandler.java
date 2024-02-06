package com.knowledge.base.configuration;

import com.knowledge.base.enums.CodeEnum;
import com.knowledge.base.response.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private HttpServletRequest httpServletRequest;

    private final String sysError = "系统出错";

    @Autowired
    I18nUtils i18nUtils;

    // get请求的对象参数校验异常
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResultData bindExceptionHandler(MissingServletRequestParameterException e) {
        String requestURI = httpServletRequest.getRequestURI();
        log.error("请求地址'{}',get方式请求参数'{}'必传", requestURI, e.getParameterName());
        return ResultData.success(CodeEnum.FAIL);
    }

    // post请求的对象参数校验异常
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResultData methodArgumentNotValidHandler(MethodArgumentNotValidException e) {
        String requestURI = httpServletRequest.getRequestURI();
        log.error("请求地址'{}',post方式请求参数异常'{}'", requestURI, e.getMessage());
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        return new ResultData(CodeEnum.FAIL.getCode(), getValidExceptionMsg(allErrors));
    }

    private String getValidExceptionMsg(List<ObjectError> errors) {
        if (!CollectionUtils.isEmpty(errors)) {
            StringBuilder sb = new StringBuilder();
            errors.forEach(error -> {
                sb.append(i18nUtils.getKey(error.getDefaultMessage())).append(";");
            });
            String msg = sb.toString();
            msg = msg.substring(0, msg.length() - 1);
            return msg;
        }
        return null;
    }

}
