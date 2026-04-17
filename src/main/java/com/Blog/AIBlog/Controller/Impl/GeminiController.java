package com.Blog.AIBlog.Controller.Impl;

import com.Blog.AIBlog.Dto.response.GeminiResponse;
import com.Blog.AIBlog.config.GeminiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/gem")
@RestController
public class GeminiController {
private final GeminiClient geminiClient;
    @GetMapping("/getContent/{category}")
    public ResponseEntity<GeminiResponse> GeminiResponse (@PathVariable String category) throws Exception {
       List<String> responseList= geminiClient.generateContent(category);
        GeminiResponse response= new GeminiResponse();
        response.setContent(responseList.get(1));
        response.setHeader(responseList.get(0));
        response.setImageUrl(responseList.get(2));
        return ResponseEntity.ok(response);
    }
}
