package com.Blog.AIBlog.Controller;

import com.Blog.AIBlog.Dto.request.PostRequest;
import com.Blog.AIBlog.Dto.request.UpdatePostRequest;
import com.Blog.AIBlog.Dto.response.PostResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPostController {
    ResponseEntity<PostResponse> addPost(PostRequest postRequest);
    ResponseEntity<PostResponse>updatePost(UpdatePostRequest postRequest);
    ResponseEntity<Void>deletePost(Long id);
    ResponseEntity<List<PostResponse>> getAllPosts();
    ResponseEntity<PostResponse> getPostWithId(Long id);
    ResponseEntity<Void>adminAutoAddPost() throws Exception;
}
