package com.Blog.AIBlog.mapper;

import com.Blog.AIBlog.Dto.request.CommentRequest;
import com.Blog.AIBlog.Dto.request.UpdateCommentRequest;
import com.Blog.AIBlog.Dto.response.CommentResponse;
import com.Blog.AIBlog.Entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface CommentMapper {

    Comment toEntity(CommentRequest request);
    CommentResponse toDto(Comment entity);
    UpdateCommentRequest toUpdateDto(Comment entity);
    Comment toUpdateEntity(UpdateCommentRequest request);
}
