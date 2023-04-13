package com.example.login.dao.repos;

import com.example.login.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findUserByAccount(String account);
    List<User> findUserByMobile(String mobile);

    Optional<User> findUserByAccountAndPassword(String account, String password);
}
