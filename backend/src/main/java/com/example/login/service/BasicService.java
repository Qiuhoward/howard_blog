package com.example.login.service;

import java.util.List;

/**
 * <p>basic crud </p>
 */
public interface BasicService {
    void add(Object request);
    void delete(int id);
    void edit(String name,int id);
    List<?> showAll();
}
