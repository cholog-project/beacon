package com.example.braveCoward.global;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExtractAuthenticationInterceptor implements HandlerInterceptor {

    private static final String BEARER_TYPE = "Bearer ";
    private static final int BEARER_TYPE_LEN = 7;

    private final JwtProvider jwtProvider;
    private final AuthContext authContext;
    private final UserIdContext userIdContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = extractAccessToken(request);
        if (token == null) {
            throw new IllegalArgumentException("Access token is required.");
        }

        Integer userId = jwtProvider.getUserId(token);
        if (userId == null) {
            throw new IllegalArgumentException("Invalid token or userId.");
        }

        authContext.setUserId(userId);
        userIdContext.setUserId(userId);

        return true;
    }

    public static String extractAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null) {
            return null;
        }
        if (!bearerToken.startsWith(BEARER_TYPE)) {
            return null;
        }
        return bearerToken.substring(BEARER_TYPE_LEN);
    }

}
