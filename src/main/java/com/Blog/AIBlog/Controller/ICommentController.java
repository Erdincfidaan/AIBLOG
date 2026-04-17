package com.Blog.AIBlog.Controller;

import com.Blog.AIBlog.Dto.request.CommentRequest;
import com.Blog.AIBlog.Dto.request.UpdateCommentRequest;
import com.Blog.AIBlog.Dto.response.CommentResponse;
import org.springframework.http.ResponseEntity;

public interface ICommentController {
    ResponseEntity<CommentResponse> addComment(CommentRequest commentRequest);
    ResponseEntity<CommentResponse> updateComment(UpdateCommentRequest commentRequest);
    ResponseEntity<Void> deleteComment(Long id);
}
