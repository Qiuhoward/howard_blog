package com.example.login.service;

import com.example.login.dto.blog.BasicDto;
import com.example.login.dto.blog.PostDto;

import java.util.List;
import java.util.Optional;

/**
 * <p>basic crud </p>
 */
public interface BasicService{

    void delete(int id,String name);
    List<?> findAll();
}
