package com.Blog.AIBlog.Service.Impl;

import com.Blog.AIBlog.Dto.request.LoginRequest;
import com.Blog.AIBlog.Dto.request.RegisterRequest;
import com.Blog.AIBlog.Dto.request.ResetPasswordRequest;
import com.Blog.AIBlog.Entity.User;
import com.Blog.AIBlog.Repository.IAuthRepository;
import com.Blog.AIBlog.Service.IAuthService;
import com.Blog.AIBlog.exception.err.*;
import com.Blog.AIBlog.mapper.AuthMapper;
import com.Blog.AIBlog.util.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements IAuthService {
    private final EmailService emailService;
    private final IAuthRepository authRepository;
    private final AuthMapper authMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    @Value("${app.baseUrl}")
    private String baseUrl;
    @Override
    public String login(LoginRequest loginRequest) {

        User control= authRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new NotFoundException("Kullanıcı bulunamadı:"+loginRequest.getEmail()));
        if(!control.isEnabled())
        {
            throw new ForbiddenException("Lütfen email adresinizi doğrulayın");
        }
        if(!passwordEncoder.matches(loginRequest.getPassword(),control.getPassword()))
        {
            throw new UnauthorizedException("Şifre hatalı");
        }

        return jwtUtil.generateToken(control.getEmail(),control.getRole());


    }

    @Override
    public String register(RegisterRequest registerRequest) {

        if(authRepository.findByEmail(registerRequest.getEmail()).isPresent())
        {
            throw new ConflictException("Bu email zaten kayıtlı");
        }
        String token= UUID.randomUUID().toString();
        User save = authMapper.toEntity(registerRequest);
        save.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        save.setRole("ADMIN");
        save.setEnabled(false);
        save.setVerificationToken(token);
        save.setTokenCreatedAt(LocalDateTime.now());
        authRepository.save(save);
        String confirmationUrl = baseUrl+"/verifyemail?token=" + token;
        emailService.sendEmail(save.getEmail(), "Email Verification", "Maili doğrulamak için tıklayınız: " + confirmationUrl);

        return "Lütfen epostanızı doğrulayın";
    }
    @Override
    public String validateVerificationToken(String token) {
        User user = authRepository.findByVerificationToken(token).orElse(null);
        if (user == null) {
            return "invalid";
        }
        if(user.getTokenCreatedAt().isBefore(LocalDateTime.now().minusHours(24)))
        {
            return "expired";
        }
        user.setVerificationToken(null);
        user.setEnabled(true);
        authRepository.save(user);
        return "valid";
    }

    @Override
    public String changePassword(ResetPasswordRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        User user = authRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("Kullanıcı bulunamadı"));
        if(!passwordEncoder.matches(request.getCurrentPassword(),user.getPassword()))
        {
            throw new UnauthorizedException("Şifre hatalı");
        }
        if(passwordEncoder.matches( request.getNewPassword(),user.getPassword()))
        {
            throw new BadRequestException("Mevcut şifre ile yeni şifre aynı olamaz");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        authRepository.save(user);

        return "Şifre başarıyla değiştirildi";
    }
}
