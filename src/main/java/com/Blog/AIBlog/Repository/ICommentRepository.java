package com.Blog.AIBlog.Repository;

import com.Blog.AIBlog.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends JpaRepository< Comment,Long> {
}
