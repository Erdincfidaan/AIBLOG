package com.Blog.AIBlog.Controller.Impl;

import com.Blog.AIBlog.Controller.ICommentController;
import com.Blog.AIBlog.Dto.request.CommentRequest;
import com.Blog.AIBlog.Dto.request.UpdateCommentRequest;
import com.Blog.AIBlog.Dto.response.CommentResponse;
import com.Blog.AIBlog.Service.ICommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/comment")
@RestController
@RequiredArgsConstructor
public class CommentControllerImpl implements ICommentController {
    private final ICommentService commentService;
    @PostMapping("/addComment")
    @Override
    public ResponseEntity<CommentResponse> addComment(@RequestBody @Valid CommentRequest commentRequest) {


        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(commentRequest));
    }
    @PutMapping("/updateComment")
    @Override
    public ResponseEntity<CommentResponse> updateComment(@RequestBody @Valid UpdateCommentRequest commentRequest) {


        return ResponseEntity.ok(commentService.updateComment(commentRequest));
    }
    @DeleteMapping("/deleteComment/{id}")
    @Override
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {

        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }



}
