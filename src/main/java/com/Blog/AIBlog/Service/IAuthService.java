package com.Blog.AIBlog.Service;

import com.Blog.AIBlog.Dto.request.LoginRequest;
import com.Blog.AIBlog.Dto.request.RegisterRequest;
import com.Blog.AIBlog.Dto.request.ResetPasswordRequest;
import com.Blog.AIBlog.Dto.response.LoginResponse;
import com.Blog.AIBlog.Dto.response.RegisterResponse;
import org.apache.coyote.BadRequestException;

public interface IAuthService {

     String login(LoginRequest loginRequest);
     String register(RegisterRequest registerRequest);
     String validateVerificationToken(String token);
     String changePassword(ResetPasswordRequest request) ;
}
