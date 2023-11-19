package com.ngolik.apigateway.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    private static final String SECRET = "ptyrJih3+1oxmbqW3GrWdY9uGkf4MbxMLVIK0e8E21mF5C8CmF0vlJFOWCeipZqt";


    public void validateToken(final String token) {
        Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
