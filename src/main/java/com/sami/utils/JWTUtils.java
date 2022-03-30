package com.sami.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sami.dto.LoginDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sami.utils.StringUtils.toLong;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
public class JWTUtils {

    @Value("${authSecret}")
    private String authSecret;

    @Value("${claims}")
    private String claims;

    @Value("${accessToken}")
    private String accessToken;

    @Value("${refreshToken}")
    private String refreshToken;

    @Value("${accessTokenExpireTime}")
    private String accessTokenExpireTime;

    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @Value("${authSecret}")
    public void setAuthSecret(String authSecret){
        JWTConstants.AUTH_SECRET = authSecret;
    }

    @Value("${claims}")
    public void setClaims(String claims){
        JWTConstants.CLAIMS = claims;
    }

    @Value("${accessToken}")
    public void setAccessToken(String accessToken){
        JWTConstants.ACCESS_TOKEN = accessToken;
    }

    @Value("${refreshToken}")
    public void setRefreshToken(String refreshToken){
        JWTConstants.REFRESH_TOKEN = refreshToken;
    }

    @Value("${accessTokenExpireTime}")
    public void setAccessTokenExpireTime(String accessTokenExpireTime){
        JWTConstants.ACCESS_TOKEN_EXPIRE_TIME = accessTokenExpireTime;
    }

    @Value("${refreshTokenExpireTime}")
    public void setRefreshTokenExpireTime(String refreshTokenExpireTime){
        JWTConstants.REFRESH_TOKEN_EXPIRE_TIME = refreshTokenExpireTime;
    }

    public static Algorithm getAlgorithm() {
        Algorithm algorithm = Algorithm.HMAC256(JWTConstants.AUTH_SECRET.getBytes());
        return algorithm;
    }

    public static String getAccessToken(HttpServletRequest request, User user, Algorithm algorithm) {
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(setAccessTokenExpireDate())
                .withIssuer(request.getRequestURL().toString())
                .withClaim(JWTConstants.CLAIMS, user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);
        return accessToken;
    }

    public static String getRefreshToken(HttpServletRequest request, User user, Algorithm algorithm) {
        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(setRefreshTokenExpireDate())
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        return refreshToken;
    }

    public static void setHeader(HttpServletResponse response, String accessToken, String refreshToken) throws IOException {
        Map<String, String> tokens = new HashMap<>();
        tokens.put(JWTConstants.ACCESS_TOKEN, accessToken);
        tokens.put(JWTConstants.REFRESH_TOKEN, refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    public static LoginDto login(HttpServletRequest request) {
        LoginDto dto = new LoginDto();
        dto.setUsername(request.getParameter("username"));
        dto.setPassword(request.getParameter("password"));
        log.info("username and password is: {}  and {}", dto.getUsername(), dto.getPassword());
        return dto;
    }

    private static Date setAccessTokenExpireDate() {
        return new Date(System.currentTimeMillis() + toLong(JWTConstants.ACCESS_TOKEN_EXPIRE_TIME));
    }

    private static Date setRefreshTokenExpireDate() {
        return new Date(System.currentTimeMillis() + toLong(JWTConstants.REFRESH_TOKEN_EXPIRE_TIME));
    }
}
