package com.Blog.AIBlog.Dto.request;

import com.Blog.AIBlog.Entity.User;
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
public class CommentRequest {

    @NotBlank(message = "Content boş olamaz")
    private String content;


    @NotNull(message = "PostId boş olamaz")
    private Long postId;
}
