package com.example.login.dao.entities;

import com.example.login.dao.Provider;
import com.example.login.dto.account.RegisterRequest;
import jakarta.persistence.*;
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

    private Integer PostId ;

    @Enumerated(EnumType.STRING)
    private Provider provider;


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
