package com.Blog.AIBlog.Controller.Impl;

import com.Blog.AIBlog.Controller.ICategoryController;
import com.Blog.AIBlog.Dto.request.CategoryRequest;
import com.Blog.AIBlog.Dto.request.UpdateCategoryRequest;
import com.Blog.AIBlog.Dto.response.CategoryResponse;
import com.Blog.AIBlog.Service.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/category")
@RestController
public class CategoryControllerImpl implements ICategoryController {

    private final ICategoryService categoryService;
    @Override
    @PostMapping("/addCategory")
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody @Valid CategoryRequest categoryRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.addCategory(categoryRequest));
    }

    @Override
    @PutMapping("/updateCategory")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody @Valid UpdateCategoryRequest categoryRequest) {

        return ResponseEntity.ok(categoryService.updateCategory(categoryRequest));
    }

    @Override
    @DeleteMapping("/deleteCategory")
    public ResponseEntity<Void> deleteCategory(Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/getCategoryWithId/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable @Valid Long id) {

        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @Override
    @GetMapping("/getAllCategories")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
