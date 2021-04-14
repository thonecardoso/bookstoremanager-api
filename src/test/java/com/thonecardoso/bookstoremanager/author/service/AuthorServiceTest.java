package com.thonecardoso.bookstoremanager.author.service;

import com.thonecardoso.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.thonecardoso.bookstoremanager.author.dto.AuthorDTO;
import com.thonecardoso.bookstoremanager.author.mapper.AuthorMapper;
import com.thonecardoso.bookstoremanager.author.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private AuthorDTOBuilder authorDTOBuilder;

    @BeforeEach
    void setUp() {
        authorDTOBuilder = AuthorDTOBuilder.builder().build();
        AuthorDTO authorDTO = authorDTOBuilder.builderAuthorDTO();
    }
}
