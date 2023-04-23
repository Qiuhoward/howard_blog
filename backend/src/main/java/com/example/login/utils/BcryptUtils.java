package com.example.login.utils;

import com.example.login.dao.user.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <雜湊 檢查 密碼>
 */

@Component
public class BcryptUtils {

    private final UserRepo userRepo;

    public BcryptUtils( UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    public Boolean checkPassword(CharSequence plainPassword,String encodedPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(plainPassword,encodedPassword);
    }


    /**
     * 先把資料庫所有密碼雜湊存到資料庫
     */
//    @Bean
//    public void hashAllPassword(){
//      List<User> userList = userRepo.findAll();
//      userList.forEach((e)->{
//          BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//          String saltPassword= passwordEncoder.encode( e.getPassword());
//          e.setPassword(saltPassword);
//          userRepository.save(e);
//      });
//    }
}
