package com.example.login.dao.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Integer> {

    List<Category> findByTitleContaining(String title);

}
