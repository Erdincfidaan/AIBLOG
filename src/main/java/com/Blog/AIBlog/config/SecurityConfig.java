package com.Blog.AIBlog.config;

import com.Blog.AIBlog.Entity.User;
import com.Blog.AIBlog.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {

        http.csrf(csrf ->csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->auth
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/posts/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/comment/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/comment/**").hasRole("USER")
                                .requestMatchers(HttpMethod.PUT, "/api/comment/**").hasRole("USER")
                                .requestMatchers(HttpMethod.DELETE, "/api/comment/**").hasRole("USER")
                                .requestMatchers(HttpMethod.POST, "/api/post/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/post/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/post/**").hasRole("ADMIN")
                                .requestMatchers("/api/category/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                        ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();






    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
