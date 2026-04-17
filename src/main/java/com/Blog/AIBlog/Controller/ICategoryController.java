package com.Blog.AIBlog.Controller;

import com.Blog.AIBlog.Dto.request.CategoryRequest;
import com.Blog.AIBlog.Dto.request.UpdateCategoryRequest;
import com.Blog.AIBlog.Dto.response.CategoryResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICategoryController {
    ResponseEntity<CategoryResponse> addCategory(CategoryRequest categoryRequest);
    ResponseEntity<CategoryResponse> updateCategory(UpdateCategoryRequest categoryRequest);
    ResponseEntity<Void> deleteCategory(Long id);
    ResponseEntity<CategoryResponse> getCategory(Long id);
    ResponseEntity<List<CategoryResponse>> getAllCategories();
}
