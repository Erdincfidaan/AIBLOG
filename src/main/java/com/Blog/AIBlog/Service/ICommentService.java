package com.Blog.AIBlog.Service;

import com.Blog.AIBlog.Dto.request.CommentRequest;
import com.Blog.AIBlog.Dto.request.UpdateCommentRequest;
import com.Blog.AIBlog.Dto.response.CommentResponse;

public interface ICommentService {
    CommentResponse addComment(CommentRequest commentRequest);
    CommentResponse updateComment(UpdateCommentRequest commentRequest);
    void deleteComment(Long id);
}
