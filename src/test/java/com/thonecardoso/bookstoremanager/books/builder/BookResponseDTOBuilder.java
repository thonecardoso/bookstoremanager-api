package com.thonecardoso.bookstoremanager.books.builder;

import com.thonecardoso.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.thonecardoso.bookstoremanager.author.dto.AuthorDTO;
import com.thonecardoso.bookstoremanager.books.dto.BookResponseDTO;
import com.thonecardoso.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.thonecardoso.bookstoremanager.publishers.dto.PublisherDTO;
import com.thonecardoso.bookstoremanager.users.builder.UserDTOBuilder;
import com.thonecardoso.bookstoremanager.users.dto.UserDTO;
import lombok.Builder;

@Builder
public class BookResponseDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String name = "Spring Boot Pro";

    @Builder.Default
    private final String isbn = "978-3-16-148410-0";

    @Builder.Default
    private final Integer pages = 200;

    @Builder.Default
    private final Integer chapters = 10;

    @Builder.Default
    private final PublisherDTO publisherId = PublisherDTOBuilder.builder().build().builderPublisherDTO();

    @Builder.Default
    private final AuthorDTO authorId = AuthorDTOBuilder.builder().build().builderAuthorDTO();

    private final UserDTO userDTO = UserDTOBuilder.builder().build().buildUserDTO();

    public BookResponseDTO buildBookResponse(){
        return new BookResponseDTO(
                id,
                name,
                isbn,
                pages,
                chapters,
                authorId,
                publisherId
        );
    }

}
