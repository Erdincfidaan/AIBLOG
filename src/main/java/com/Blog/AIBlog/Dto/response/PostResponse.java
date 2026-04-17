package com.Blog.AIBlog.Dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    @NotNull(message = "id boş olamaz")
    private Long id;
    @NotBlank(message = "content boş olamaz")
    private String content;
    @NotBlank(message = "header boş olamaz")
    private String header;
    @NotBlank(message = "imageUrl boş olamaz")
    private String imageURL;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
