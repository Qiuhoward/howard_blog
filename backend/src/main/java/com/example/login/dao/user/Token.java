package com.example.login.dao.user;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;

    private String token;

    private boolean expired;

    private boolean revoked;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}