package com.example.login.service;


import com.example.login.exception.InternalServerException;

import java.util.List;

/**
 * <p>basic crud </p>
 */
public interface BasicService{

    void delete(int id,String name) throws InternalServerException;
    List<?> findAll();
}
