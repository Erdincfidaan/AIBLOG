package com.Blog.AIBlog.Dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {

    @NotBlank(message = "Şifre girilmelidir")
    private String currentPassword;
    @NotBlank(message = "Yeni şifre girilmelidir")
    private String newPassword;



}
