package com.Blog.AIBlog.Dto.request;

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
public class UpdatePostRequest {
    @NotNull(message = "id boş olamaz")
    private Long id;
    @NotBlank(message = "content boş olamaz")
    private String content;
    @NotBlank(message = "header boş olamaz")
    private String header;
    @NotBlank(message = "imageUrl boş olamaz")
    private String imageURL;
    @NotNull(message = "userId boş olamaz")
    private Long userId;
    @NotNull(message = "categoryId boş olamaz")
    private Long categoryId;
}
