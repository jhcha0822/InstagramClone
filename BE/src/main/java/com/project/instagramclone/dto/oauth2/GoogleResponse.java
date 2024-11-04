package com.project.instagramclone.dto.oauth2;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class GoogleResponse implements OAuth2Response {
    private final Map<String, Object> attribute;

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.getOrDefault("sub", "").toString();
    }

    @Override
    public String getName() {
        return attribute.getOrDefault("name", "Unknown").toString();
    }

    @Override
    public String getEmail() {
        return attribute.getOrDefault("email", "NoEmailProvided").toString();
    }
}
