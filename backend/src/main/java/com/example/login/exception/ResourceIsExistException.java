package com.example.login.exception;



import lombok.Getter;
import lombok.Setter;

/**
 * <資料已存在錯誤></資料已存在錯誤>
 */
@Getter
@Setter
public class ResourceIsExistException extends RuntimeException {
    private Class<?> clazz;
    private String key;
    private Object value;

    public ResourceIsExistException(Class<?> clazz, String key,Object  value) {
        super(String.format("%s is exist %s : %s", clazz, key, value));
        this.clazz = clazz;
        this.key = key;
        this.value = value;
    }
}
