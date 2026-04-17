package com.Blog.AIBlog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "category_name",nullable = false)
    private String categoryName;
    @Column(name = "subcategory",nullable = true)
    private String subCategory;

    @OneToMany(mappedBy = "category")
    private List<Post> post;


}
