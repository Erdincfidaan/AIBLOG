package com.Blog.AIBlog.Service.Impl;

import com.Blog.AIBlog.Dto.request.CommentRequest;
import com.Blog.AIBlog.Dto.request.UpdateCommentRequest;
import com.Blog.AIBlog.Dto.response.CommentResponse;
import com.Blog.AIBlog.Entity.Comment;
import com.Blog.AIBlog.Entity.Post;
import com.Blog.AIBlog.Entity.User;
import com.Blog.AIBlog.Repository.IAuthRepository;
import com.Blog.AIBlog.Repository.ICommentRepository;
import com.Blog.AIBlog.Repository.IPostRepository;
import com.Blog.AIBlog.Service.ICommentService;
import com.Blog.AIBlog.exception.err.ForbiddenException;
import com.Blog.AIBlog.exception.err.NotFoundException;
import com.Blog.AIBlog.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {
    private final ICommentRepository commentRepository;
    private final IAuthRepository authRepository;
    private final IPostRepository postRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentResponse addComment(CommentRequest commentRequest) {


        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated())
        {
            throw new ForbiddenException("Yetkisiz işlem");
        }
        String email=authentication.getName();
        User userControl = authRepository.findByEmail(email).orElseThrow(()->new NotFoundException("Kullanıcı bulunamadı bulunamadı:"+email));
        Post postControl= postRepository.findById(commentRequest.getPostId()).orElseThrow(()->new NotFoundException("Post bulunamadı:"+commentRequest.getPostId()));

        Comment entity= commentMapper.toEntity(commentRequest);
        entity.setPost(postControl);
        entity.setUser(userControl);
        Comment saved=commentRepository.save(entity);
        return commentMapper.toDto(saved);
    }

    @Override
    public CommentResponse updateComment(UpdateCommentRequest commentRequest) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated())
        {
            throw new ForbiddenException("Yetkisiz işlem");
        }
        String email=authentication.getName();
        User userControl = authRepository.findByEmail(email).orElseThrow(()->new NotFoundException("Kullanıcı  bulunamadı:"+email));

        Comment commentControl= commentRepository.findById(commentRequest.getId()).orElseThrow(()-> new NotFoundException("Geçerli yorum bulunamadı:"+commentRequest.getId()));
        if(!(commentControl.getUser().getEmail().equals(userControl.getEmail())) && !(userControl.getRole().equals("ADMIN")))
        {
            throw new ForbiddenException("Yetkisiz işlem");
        }
        Post postControl= postRepository.findById(commentRequest.getPostId()).orElseThrow(()->new RuntimeException("Post bulunamadı"));
        Comment entity= commentMapper.toUpdateEntity(commentRequest);
        entity.setPost(postControl);
        entity.setUser(userControl);
        Comment saved=commentRepository.save(entity);

        return commentMapper.toDto(saved);
    }

    @Override
    public void deleteComment(Long id)
    {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated())
        {
            throw new ForbiddenException("Yetkisiz işlem");
        }
        String email=authentication.getName();
        User userControl = authRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("Kullanıcı  bulunamadı:"+email));
        Comment commentControl= commentRepository.findById(id).orElseThrow(()-> new NotFoundException("Yorum bulunamadı:"+id));
        if(!(userControl.getEmail().equals(commentControl.getUser().getEmail())) && !(userControl.getRole().equals("ADMIN")))
        {
            throw new ForbiddenException("Yetkisiz işlem");
        }

        commentRepository.deleteById(commentControl.getId());


    }
}
