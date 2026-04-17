package com.Blog.AIBlog.Dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentRequest {
    @NotNull(message = "id boş olamaz")
    private Long id;
    @NotBlank(message = "content boş olamaz")
    private String content;
    @NotNull(message = "postId boş olamaz")
    private Long postId;
}
