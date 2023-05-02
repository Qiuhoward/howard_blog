package com.example.login.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TokenRepo extends JpaRepository<Token,Integer> {
    List<Token> findTokenByUser(User user);


}
