package com.example.springjwt_maven.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private SecretKey secretKey;
    /**
     * -- GETTER --
     * Access Token 만료시간(ms) 반환
     */
    // access/refresh 용 만료시간 분리
    @Getter
    @Value("${jwt.access.expired-ms}") private long accessExpiredMs;
    /**
     * -- GETTER --
     * Refresh Token 만료시간(ms) 반환
     */
    @Getter
    @Value("${jwt.refresh.expired-ms}") private long refreshExpiredMs;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {

        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }
//    public JWTUtil(@Value("${spring.jwt.secret}") String base64Secret) {
//        // 1) Base64 → raw bytes
//        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
//        // 2) HS256용 SecretKey 객체 생성
//        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
//    }

    public int getUserId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userId", Integer.class);
    }

    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String createAccessToken(int userId, String username, String role) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessExpiredMs))
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(int userId) {
        Date now = new Date();
        return Jwts.builder()
                .claim("userId", userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshExpiredMs))
                .signWith(secretKey)
                .compact();
    }

    // 서명 검증까지 수행하고 토큰의 Claims를 반환
    public Claims parseClaims(String token) {
        return Jwts.parser()                     // JwtParserBuilder 생성
                .verifyWith(secretKey)        // 서명 검증용 SecretKey 지정
                .build()                      // JwtParser 완성
                .parseSignedClaims(token)     // parseSignedClaims → Jwt<Header,Claims>
                .getPayload();                // Payload인 Claims 반환
    }
}
