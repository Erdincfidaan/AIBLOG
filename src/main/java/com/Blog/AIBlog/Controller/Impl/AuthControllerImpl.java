package com.Blog.AIBlog.Controller.Impl;

import com.Blog.AIBlog.Controller.IAuthController;
import com.Blog.AIBlog.Dto.request.LoginRequest;
import com.Blog.AIBlog.Dto.request.RegisterRequest;
import com.Blog.AIBlog.Dto.request.ResetPasswordRequest;
import com.Blog.AIBlog.Dto.response.LoginResponse;
import com.Blog.AIBlog.Dto.response.RegisterResponse;
import com.Blog.AIBlog.Service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/auth")
@RequiredArgsConstructor
@RestController
public class AuthControllerImpl implements IAuthController {

    private final IAuthService authService;
    @PostMapping("/login")
    @Override
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));

    }
    @PostMapping("/register")
    @Override
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequest));
    }
    @GetMapping("/verifyemail")
    @Override
    public ResponseEntity<String> validateVerificationToken(String token) {
        return ResponseEntity.ok(authService.validateVerificationToken(token));
    }
    @PostMapping("changepassword")
    @Override
    public ResponseEntity<String> changePassword(@RequestBody ResetPasswordRequest request) {

        return ResponseEntity.ok(authService.changePassword(request));

    }


}
