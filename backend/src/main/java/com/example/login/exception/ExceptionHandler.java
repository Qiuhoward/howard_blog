package com.example.login.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

/**
 * <全局錯誤處理></全局錯誤處理>
 */
@RestControllerAdvice
public class ExceptionHandler {
    /**
     * 參數錯誤處理
     * @param ex
     *        錯誤
     * @return errorMap
     *        錯誤訊息列表
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errorMap.put(error.getField(), error.getDefaultMessage())
        );
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(InternalServerException.class)
    public Map<String,String> handleInternalServer(InternalServerException ex){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Internal_server_error",ex.getMessage());
        return errorMap;
    }

}
