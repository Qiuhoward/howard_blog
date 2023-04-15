package com.example.login.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findUserByUserName(String email);
    List<User> findUserByMobile(String mobile);

    Optional<User> findUserByUserNameAndPassword(String email, String password);
}