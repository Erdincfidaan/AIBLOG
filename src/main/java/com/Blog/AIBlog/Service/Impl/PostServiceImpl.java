package com.Blog.AIBlog.Service.Impl;

import com.Blog.AIBlog.Dto.request.PostRequest;
import com.Blog.AIBlog.Dto.request.UpdatePostRequest;
import com.Blog.AIBlog.Dto.response.PostResponse;
import com.Blog.AIBlog.Entity.Category;
import com.Blog.AIBlog.Entity.Post;
import com.Blog.AIBlog.Entity.User;
import com.Blog.AIBlog.Repository.IAuthRepository;
import com.Blog.AIBlog.Repository.ICategoryRepository;
import com.Blog.AIBlog.Repository.IPostRepository;
import com.Blog.AIBlog.Service.IPostService;
import com.Blog.AIBlog.config.GeminiClient;
import com.Blog.AIBlog.exception.err.ExternalServiceException;
import com.Blog.AIBlog.exception.err.NotFoundException;
import com.Blog.AIBlog.mapper.PostMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {

    private final IPostRepository postRepository;
    private final ICategoryRepository categoryRepository;
    private final IAuthRepository authRepository;
    private final PostMapper postMapper;
    private final GeminiClient geminiClient;
    @Override
    public PostResponse addPost(PostRequest postRequest) {

        Category categoryControl= categoryRepository.findById(postRequest.getCategoryId()).orElseThrow(()->new NotFoundException("Kategori bulunamadı:"+postRequest.getCategoryId()));
        User userControl= authRepository.findById(postRequest.getUserId()).orElseThrow(()-> new NotFoundException("Kullanıcı bulunamadı: " + postRequest.getUserId()));

        Post entiy=postMapper.toEntity(postRequest);
        entiy.setCreatedAt(LocalDateTime.now());
        entiy.setUpdatedAt(LocalDateTime.now());
        entiy.setCategory(categoryControl);
        entiy.setUser(userControl);
        Post save= postRepository.save(entiy);
        return  postMapper.toDto(save);
    }

    @Override
    public PostResponse updatePost(UpdatePostRequest postRequest) {
        Category categoryControl= categoryRepository.findById(postRequest.getCategoryId()).orElseThrow(()->new NotFoundException("Kategori bulunamadı:"+postRequest.getCategoryId()));
        User userControl= authRepository.findById(postRequest.getUserId()).orElseThrow(()-> new NotFoundException("Kullanıcı bulunamadı: " + postRequest.getUserId()));
        Post postControl= postRepository.findById(postRequest.getId()).orElseThrow(()-> new NotFoundException("Post bulunamadı: " + postRequest.getId()));
        Post entity=postMapper.toUpdateEntity(postRequest);
        entity.setCreatedAt(postControl.getCreatedAt());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setCategory(categoryControl);
        entity.setUser(userControl);
        Post save=postRepository.save(entity);
        PostResponse response =postMapper.toDto(save);
        return response;
    }

    @Override
    public List<PostResponse> getAllPosts() {

        List<PostResponse> responseEntity= postRepository.findAll().stream().map(
            post->{
                PostResponse postResponse = new PostResponse();
                BeanUtils.copyProperties(post,postResponse);
                return postResponse;
            }).collect(Collectors.toList());

            return responseEntity;


    }

    @Override
    public PostResponse getPostWithId(Long id) {

        Post postcontrol=postRepository.findById(id).orElseThrow(()-> new NotFoundException("Post bulunamadı: " + id));
        return postMapper.toDto(postcontrol);

    }

    @Override
    public void deletePost(Long id) {

        Post postControl= postRepository.findById(id).orElseThrow(()-> new NotFoundException("Post bulunamadı: " + id));
        postRepository.deleteById(postControl.getId());

    }
    @Scheduled(cron = "0 0 9 * * ?", zone = "Europe/Istanbul")
    @Transactional
    @Override
    public void  geminiAutoAddPost() throws Exception {

        User user=authRepository.findByEmail("admin@gmail.com").orElseThrow(()-> new NotFoundException("Admin kullanıcısı bulunamadı: "));
        Category category=categoryRepository.findByCategoryName("Software").orElseThrow(()->new NotFoundException("Kategori bulunamadı:"));
        try{
            List<String> content=geminiClient.generateContent(category.getCategoryName());
            Post post= new Post();
            post.setCategory(category);
            post.setHeader(content.get(0));
            post.setContent(content.get(1));
            post.setImageURL(content.get(2));
            post.setCreatedAt(LocalDateTime.now());
            post.setUpdatedAt(LocalDateTime.now());
            post.setUser(user);
            postRepository.save(post);
        }catch (Exception e)
        {
            throw new ExternalServiceException("Gemini içerik üretimi başarısız", e);
        }


    }

    @Override
    public void  adminAutoAddPost() throws Exception {

        User user=authRepository.findByEmail("admin@gmail.com").orElseThrow(()-> new NotFoundException("Admin kullanıcısı bulunamadı: "));
        Category category=categoryRepository.findByCategoryName("Software").orElseThrow(()->new NotFoundException("Kategori bulunamadı:"));
        try{
            List<String> content=geminiClient.generateContent(category.getCategoryName());
            Post post= new Post();
            post.setCategory(category);
            post.setHeader(content.get(0));
            post.setContent(content.get(1));
            post.setImageURL(content.get(2));
            post.setCreatedAt(LocalDateTime.now());
            post.setUpdatedAt(LocalDateTime.now());
            post.setUser(user);
            postRepository.save(post);
        } catch (Exception e) {
            throw new ExternalServiceException("Gemini içerik üretimi başarısız", e);
        }

    }
}
