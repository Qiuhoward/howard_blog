package com.example.login.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
    private Class<?> clazz;
    private String key;
    private Object value;

    public ResourceNotFoundException(Class<?> clazz, String key,Object  value) {
        super(String.format("%s not found with %s : %s", clazz, key, value));
        this.clazz = clazz;
        this.key = key;
        this.value = value;
    }
}
