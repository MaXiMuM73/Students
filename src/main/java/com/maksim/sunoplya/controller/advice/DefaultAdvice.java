package com.maksim.sunoplya.controller.advice;

import com.maksim.sunoplya.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class DefaultAdvice {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({AppException.class})
    public String handleAppException(AppException e) {
        log.error(e.getMessage());
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            log.debug(stackTraceElement.toString());
        }
        return e.getMessage();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public String handleMethodArgumentNotValidException
            (MethodArgumentNotValidException e) {
        StringBuilder message = new StringBuilder();
        for (ObjectError fieldError : e.getBindingResult().getFieldErrors()) {
            message.append(fieldError.getDefaultMessage()).append(" ");
        }
        return message.toString();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public String handleHttpMessageNotReadableException
            (HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return "Incorrect data type.";
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public String handleHttpRequestMethodNotSupportedException
            (HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return "Incorrect request method.";
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Throwable.class})
    public String handleUnknownException(Exception e) {
        log.error("Unknown error.");
        e.printStackTrace();
        return "Unknown error.";
    }
}