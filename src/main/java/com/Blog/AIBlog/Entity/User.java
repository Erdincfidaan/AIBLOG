package com.Blog.AIBlog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username",nullable = false)
    private String username;
    @Column(name = "email",nullable = false,unique = true)
    private String email;
    @Column(name = "ROLE", nullable=false)
    private String role;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean enabled;
    private String verificationToken;
    private LocalDateTime tokenCreatedAt;







}
