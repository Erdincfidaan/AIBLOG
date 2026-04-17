package com.Blog.AIBlog.mapper;


import com.Blog.AIBlog.Dto.request.CategoryRequest;
import com.Blog.AIBlog.Dto.request.UpdateCategoryRequest;
import com.Blog.AIBlog.Dto.response.CategoryResponse;
import com.Blog.AIBlog.Entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel="Spring")
public interface CategoryMapper {

    CategoryResponse toDto(Category entity);
    Category toRequestEntity(CategoryRequest dto);
    Category toUpdateEntity(UpdateCategoryRequest dto);

}
