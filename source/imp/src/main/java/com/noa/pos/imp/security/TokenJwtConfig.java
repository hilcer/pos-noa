package com.noa.pos.imp.security;

import java.security.Key;

public class TokenJwtConfig {

    public static final Key SECRET_KEY = io.jsonwebtoken.Jwts.SIG.HS256.key().build();
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json";

}
