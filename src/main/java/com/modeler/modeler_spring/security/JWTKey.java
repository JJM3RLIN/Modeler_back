package com.modeler.modeler_spring.security;

import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;


public class JWTKey {
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
}
