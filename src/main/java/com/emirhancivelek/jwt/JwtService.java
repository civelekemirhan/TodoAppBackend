package com.emirhancivelek.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "O6V/P9T2IdG4qMiWsdzKI+5x2S8jrBgvgcLI6SQBeJI=";

    public String generateToken(UserDetails userDetails){
       return Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))
                .signWith(getKey(),SignatureAlgorithm.HS256)
                .compact();

    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token).getBody();

    }

    public <T> T exportToken(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = getClaims(token);
        return claimsTFunction.apply(claims);
    }

    public String getUsername(String token){
       return exportToken(token,Claims::getSubject);
    }
    public boolean isTokenValid(String token){
        Date expireDate = exportToken(token,Claims::getExpiration);
        return new Date().before(expireDate);
    }


    public Key getKey(){
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }

}
