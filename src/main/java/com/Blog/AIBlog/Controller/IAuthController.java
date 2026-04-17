package com.Blog.AIBlog.Controller;

import com.Blog.AIBlog.Dto.request.LoginRequest;
import com.Blog.AIBlog.Dto.request.RegisterRequest;
import com.Blog.AIBlog.Dto.request.ResetPasswordRequest;
import com.Blog.AIBlog.Dto.response.LoginResponse;
import com.Blog.AIBlog.Dto.response.RegisterResponse;
import org.springframework.http.ResponseEntity;

public interface IAuthController {
    ResponseEntity<String> login(LoginRequest loginRequest);
    ResponseEntity<String> register(RegisterRequest registerRequest);
    ResponseEntity<String> validateVerificationToken(String token);
    ResponseEntity<String> changePassword(ResetPasswordRequest request);
}
