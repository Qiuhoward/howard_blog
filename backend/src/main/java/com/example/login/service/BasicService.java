package com.example.login.service;


import java.util.List;

/**
 * <p>basic crud </p>
 */
public interface BasicService{

    void delete(int id,String name);
    List<?> findAll();
}
