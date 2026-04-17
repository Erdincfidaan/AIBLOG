package com.Blog.AIBlog.Controller.Impl;

import com.Blog.AIBlog.Controller.IPostController;
import com.Blog.AIBlog.Dto.request.PostRequest;
import com.Blog.AIBlog.Dto.request.UpdatePostRequest;
import com.Blog.AIBlog.Dto.response.PostResponse;
import com.Blog.AIBlog.Service.IPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/post")
@RequiredArgsConstructor
@RestController
public class PostControllerImpl implements IPostController {

 private  final IPostService postService;
    @PostMapping("/addPost")
    @Override
    public ResponseEntity<PostResponse> addPost(@RequestBody @Valid PostRequest postRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(postService.addPost(postRequest));
    }
    @PutMapping("/updatePost")
    @Override
    public ResponseEntity<PostResponse> updatePost(@RequestBody @Valid UpdatePostRequest postRequest) {
        return ResponseEntity.ok(postService.updatePost(postRequest));
    }
    @DeleteMapping("/deletePost/{id}")
    @Override
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/getAllPosts")
    @Override
    public ResponseEntity<List<PostResponse>> getAllPosts() {


        return  ResponseEntity.ok(postService.getAllPosts());
    }
    @GetMapping("/getPostWithId/{id}")
    @Override
    public ResponseEntity<PostResponse> getPostWithId(@PathVariable Long id) {


        return ResponseEntity.ok(postService.getPostWithId(id));
    }
    @PostMapping("/adminAutoAddPost")
    @Override
    public ResponseEntity<Void> adminAutoAddPost() throws Exception {
        postService.adminAutoAddPost();
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
