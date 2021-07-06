package com.dkt.blogboot.exception;

import com.dkt.blogboot.resp.CommonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author 窦康泰
 * @date 2021/07/06
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
    private final static Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResp validExceptionHandler(MethodArgumentNotValidException e) {
        return getCommonExceptionResp(e.getBindingResult());
    }

    @ExceptionHandler(BindException.class)
    public CommonResp validExceptionHandler(BindException e) {
        return getCommonExceptionResp(e.getBindingResult());
    }

    @ExceptionHandler(Exception.class)
    public CommonResp validExceptionHandler(Exception e) {
        CommonResp commonResp = new CommonResp();
        log.error("系统异常：", e);
        commonResp.setSuccess(false);
        commonResp.setMessage("系统出现异常，请联系管理员");
        return commonResp;
    }

    private CommonResp getCommonExceptionResp(BindingResult bindingResult) {
        CommonResp<Object> resp = new CommonResp<>();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        StringBuilder message = new StringBuilder();
        for (ObjectError error : allErrors) {
            message.append(error.getDefaultMessage()).append(";");
        }
        log.warn("参数校验失败：{}", message.toString());
        resp.setSuccess(false);
        resp.setMessage(message.toString());
        return resp;
    }
}
