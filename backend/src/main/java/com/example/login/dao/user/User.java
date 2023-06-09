package com.example.login.dao.user;

import com.example.login.dao.post.Post;
import com.example.login.dto.account.RegisterRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@Getter
@Setter
@Entity
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String name;
    private String userName;
    private String password;
    private String mobile;
    private Date createAt;
    private Date lastTime;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Post> posts=new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Token>tokens=new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;


    public User(RegisterRequest request) {
        this.password=request.getPassword();
        this.userName=request.getUserName();
        this.mobile=request.getMobile();
        this.createAt=new Date();
        this.lastTime=new Date();
        this.name=request.getName();
        this.role=Role.USER;
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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }//授權部分
    @Override
    public String getUsername() {
        return userName;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
