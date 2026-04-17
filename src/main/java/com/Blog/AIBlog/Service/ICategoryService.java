package com.Blog.AIBlog.Service;

import com.Blog.AIBlog.Dto.request.CategoryRequest;
import com.Blog.AIBlog.Dto.request.UpdateCategoryRequest;
import com.Blog.AIBlog.Dto.response.CategoryResponse;

import java.util.List;

public interface ICategoryService {

     CategoryResponse addCategory(CategoryRequest categoryRequest);
     CategoryResponse updateCategory(UpdateCategoryRequest categoryRequest);
     void deleteCategory(Long id);
     CategoryResponse getCategory(Long id);
     List<CategoryResponse> getAllCategories();
}
