package com.mock.common.handle;

import cn.hutool.json.JSONObject;
import com.mock.common.exception.BizException;
import com.mock.common.result.Result;
import com.mock.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

/**
 * 调整异常处理的HTTP状态码，丰富异常处理类型
 *
 * @author zhao
 * @since 2022-06-09 17:45
 * @description: 全局系统异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public static final String BASE_ERROR_MESSAGE = "服务异常，请联系管理员！";

    /**
     * 处理请求参数格式错误
     *
     * @param request
     * @param e
     * @return
     * @RequestBody 上使用@Valid实体上使用@NotNull等，验证失败后抛出的异常是MethodArgumentNotValidException异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result<String> methodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        log.error("接口:{}，发生参数校验异常，异常摘要:{}", request.getRequestURI(), errorMessage);
        if (StringUtils.isNotBlank(errorMessage)) {
            return Result.failed(errorMessage);
        }
        return Result.failed(BASE_ERROR_MESSAGE);
    }

    /**
     * 表单绑定到 java bean 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    public <T> Result<T> processException(HttpServletRequest request, BindException e) {
        JSONObject msg = new JSONObject();
        e.getAllErrors().forEach(error -> {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                msg.set(fieldError.getField(),
                        fieldError.getDefaultMessage());
            } else {
                msg.set(error.getObjectName(),
                        error.getDefaultMessage());
            }
        });
        log.error("接口:{}，发生参数校验异常，异常摘要:{}", request.getRequestURI(), msg.toString());
        return Result.failed(ResultCode.PARAM_ERROR, msg.toString());
    }


    /**
     * 捕获自定义异常
     * @param e
     * @param <T>
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BizException.class)
    public <T> Result<T> handleException(HttpServletRequest request, BizException e) {
        log.error("接口:{}发生异常,异常摘要:{}", request.getRequestURI(), e.getMessage(), e);
        return Result.failed(e.getLocalizedMessage());
    }

    /**
     * 捕获所有异常
     *
     * @param request
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public <T> Result<T> handleException(HttpServletRequest request, Exception e) {
        log.error("接口:{}发生异常,异常摘要:{}", request.getRequestURI(), e.getMessage(), e);
        return Result.failed(e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public <T> Result<T> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("非法参数异常，异常原因：{}", e.getMessage(), e);
        return Result.failed(e.getMessage());
    }

}
