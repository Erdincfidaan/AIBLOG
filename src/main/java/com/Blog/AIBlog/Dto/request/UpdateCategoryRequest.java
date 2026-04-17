package com.Blog.AIBlog.Dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryRequest {
    @NotNull(message = "id alanı boş olamaz")
    private Long id;
    @NotBlank(message = "categoryName boş olamaz")
    private String categoryName;
    private String subCategory;
}
