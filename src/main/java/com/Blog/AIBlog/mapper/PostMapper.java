package com.Blog.AIBlog.mapper;

import com.Blog.AIBlog.Dto.request.PostRequest;
import com.Blog.AIBlog.Dto.request.UpdatePostRequest;
import com.Blog.AIBlog.Dto.response.PostResponse;
import com.Blog.AIBlog.Entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface PostMapper {

    Post toEntity(PostRequest request);
    PostResponse toDto(Post entity);
    Post toUpdateEntity(UpdatePostRequest request);

}
