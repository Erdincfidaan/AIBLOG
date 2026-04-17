package com.Blog.AIBlog.Service.Impl;

import com.Blog.AIBlog.Dto.request.CategoryRequest;
import com.Blog.AIBlog.Dto.request.UpdateCategoryRequest;
import com.Blog.AIBlog.Dto.response.CategoryResponse;
import com.Blog.AIBlog.Entity.Category;
import com.Blog.AIBlog.Repository.ICategoryRepository;
import com.Blog.AIBlog.Service.ICategoryService;
import com.Blog.AIBlog.exception.err.ConflictException;
import com.Blog.AIBlog.exception.err.NotFoundException;
import com.Blog.AIBlog.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    private final CategoryMapper mapper;
    @Override
    public CategoryResponse addCategory(CategoryRequest categoryRequest) {
        Category entity= mapper.toRequestEntity(categoryRequest);
        categoryRepository.findByCategoryName(entity.getCategoryName())
                .ifPresent(c->{throw new ConflictException("Bu kategori zaten mevcut");});

        Category saved=categoryRepository.save(entity);
        CategoryResponse response= mapper.toDto(saved);
        return response;

    }

    @Override
    public CategoryResponse updateCategory(UpdateCategoryRequest categoryRequest) {

        Category entity= mapper.toUpdateEntity(categoryRequest);
        Category control=categoryRepository.findById(categoryRequest.getId())
                .orElseThrow(()->new NotFoundException("Güncellenecek kategori bulunamadı"+categoryRequest.getId()));

        Category save=categoryRepository.save(entity);
        return mapper.toDto(entity);

    }

    @Override
    public void deleteCategory(Long id) {
        Category control=categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Silinecek kategori bulunamadı"+id));
        categoryRepository.deleteById(id);

    }

    @Override
    public CategoryResponse getCategory(Long id) {

        Category control = categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Listelenecek kategori bulunamadı"+id));
        return mapper.toDto(control);

    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        List<Category> categoryResponseList = categoryRepository.findAll();
        List<CategoryResponse> response = categoryResponseList.stream().map(mapper::toDto).toList();
        return response;




    }
}
