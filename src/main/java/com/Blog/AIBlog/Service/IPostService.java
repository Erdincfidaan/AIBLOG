package com.Blog.AIBlog.Service;

import com.Blog.AIBlog.Dto.request.PostRequest;
import com.Blog.AIBlog.Dto.request.UpdatePostRequest;
import com.Blog.AIBlog.Dto.response.PostResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPostService {
    PostResponse addPost(PostRequest postRequest);
    PostResponse updatePost(UpdatePostRequest postRequest);
    List<PostResponse> getAllPosts();
    PostResponse getPostWithId(Long id);
    void deletePost(Long id);
    void geminiAutoAddPost() throws Exception;
    void adminAutoAddPost() throws Exception;

}
