package com.thonecardoso.bookstoremanager.users.builder;

import com.thonecardoso.bookstoremanager.users.dto.JwtRequest;
import lombok.Builder;

@Builder
public class JwtRequestBuilder {

    @Builder.Default
    private String username = "thonecardoso";

    @Builder.Default
    private String password = "123456";

    public JwtRequest buildJwtRequest() {
        return new JwtRequest(username, password);
    }
}
