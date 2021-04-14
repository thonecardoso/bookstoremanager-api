package com.thonecardoso.bookstoremanager.author.builder;

import com.thonecardoso.bookstoremanager.author.dto.AuthorDTO;
import lombok.Builder;

@Builder
public class AuthorDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String name = "Thone Cardoso";

    @Builder.Default
    private final int age = 31;

    public AuthorDTO builderAuthorDTO() {
        return new AuthorDTO(id, name, age);
    }
}
