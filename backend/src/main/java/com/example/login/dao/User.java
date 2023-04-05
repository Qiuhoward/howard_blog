package com.example.login.dao;

import com.example.login.dto.RegisterRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.Hibernate;
import java.util.Date;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private Integer age;
    private String name;
    private String email;
    private String account;
    private String password;
    private String mobile;
    private Date createAt;
    private Date lastTime;


    public User(RegisterRequest request) {
        this.age=request.getAge();
        this.account=request.getAccount();
        this.password=request.getPassword1();
        this.email=request.getEmail();
        this.mobile=request.getMobile();
        this.createAt=new Date();
        this.lastTime=new Date();
        this.name=request.getName();
    }

    public User() {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return userId != null && Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
