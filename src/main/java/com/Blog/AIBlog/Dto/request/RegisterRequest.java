package com.Blog.AIBlog.Dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "username boş olamaz")
    private String username;
    @NotBlank(message = "email boş olamaz")
    private String email;
    @NotBlank(message = "password boş olamaz")
    private String password;
}
