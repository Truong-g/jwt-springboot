package com.spring.jwt.jwt;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class JWTConfig {

    @Value("${jwt.url:/jwt/login}")
    private String url;
    @Value("${jwt.header:Authorization}")
    private String header;
    @Value("${jwt.prefix:Bearer}")
    private String prefix;
    @Value("${jwt.expiration:#{60*60}}")
    private int expiration;
    @Value("${jwt.secret:665468576D5A7134743777217A25432A462D4A614E645267556B586E32723575}")
    private String secret;
}
