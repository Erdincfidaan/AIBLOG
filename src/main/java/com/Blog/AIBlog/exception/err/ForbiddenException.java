package com.Blog.AIBlog.exception.err;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String message)
    {
        super(message);
    }
}
