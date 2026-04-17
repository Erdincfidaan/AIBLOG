package com.Blog.AIBlog.mapper;

import com.Blog.AIBlog.Dto.request.RegisterRequest;
import com.Blog.AIBlog.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface AuthMapper {

    User toEntity(RegisterRequest request);


}
