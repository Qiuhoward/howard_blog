package com.example.login.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    List<User> findUserByAccount(String account);
    List<User> findUserByMobile(String mobile);

    Optional<User> findUserByAccountAndPassword(String account, String password);
}
