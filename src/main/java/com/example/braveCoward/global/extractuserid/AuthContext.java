package com.example.braveCoward.global.extractuserid;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class AuthContext {

    private Integer userId;

    public Integer getUserId() {
        if (userId == null) {
            throw new IllegalArgumentException("Access token is required or invalid.");
        }
        return userId;
    }

    public boolean isAnonymous() {
        return userId == null;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
