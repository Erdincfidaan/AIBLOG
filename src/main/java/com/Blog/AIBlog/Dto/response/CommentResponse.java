package com.Blog.AIBlog.Dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    @NotNull(message = "id boş olamaz")
    private Long id;
    @NotBlank(message = "content boş olamaz")
    private String content;
}
