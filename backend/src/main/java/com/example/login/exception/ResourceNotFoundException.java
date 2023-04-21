package com.example.login.exception;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String key;
    private Integer value;

    public ResourceNotFoundException(Class<?>clazz, String key, Integer value) {
        super(String.format("%s not found with %s : %d", clazz, key, value));
        this.resourceName = resourceName;
        this.key = key;
        this.value = value;
    }
}
