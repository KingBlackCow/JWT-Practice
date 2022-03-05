package com.cos.jwt.jwt;

public interface JwtProperties {
    String SECRET = "cos";
    int EXPIRATION_TIME = 864000000;//10일
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
