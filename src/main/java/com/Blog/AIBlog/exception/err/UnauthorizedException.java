package com.Blog.AIBlog.exception.err;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(String message)
    {
        super(message);
    }
}
