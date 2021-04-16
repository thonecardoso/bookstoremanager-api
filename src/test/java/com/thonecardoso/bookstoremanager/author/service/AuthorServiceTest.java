package com.thonecardoso.bookstoremanager.author.service;

import com.thonecardoso.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.thonecardoso.bookstoremanager.author.dto.AuthorDTO;
import com.thonecardoso.bookstoremanager.author.entity.Author;
import com.thonecardoso.bookstoremanager.author.exception.AuthorAlreadyExistsException;
import com.thonecardoso.bookstoremanager.author.exception.AuthorNotFoundException;
import com.thonecardoso.bookstoremanager.author.mapper.AuthorMapper;
import com.thonecardoso.bookstoremanager.author.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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

    @Test
    void whenNewAuthorIsInformedThenItShouldBeCreated() {
        //given
        AuthorDTO expectedAuthorToCreateDTO = authorDTOBuilder.builderAuthorDTO();
        Author expectedCreatedAuthor = authorMapper.toModel(expectedAuthorToCreateDTO);

        //when
        // normal import  Mockito.when...
        when(authorRepository.save(expectedCreatedAuthor)).thenReturn(expectedCreatedAuthor);
        when(authorRepository.findByName(expectedAuthorToCreateDTO.getName())).thenReturn(Optional.empty());

        AuthorDTO createdAuthorDTO = authorService.create(expectedAuthorToCreateDTO);

        //then
        // normal import MatcherAssert.assertThat(...
        assertThat(createdAuthorDTO, is(equalTo(expectedAuthorToCreateDTO)));

    }

    @Test
    void whenExistingAuthorIsInformedThenAnExceptionShouldBeThrown() {
        //given
        AuthorDTO expectedAuthorToCreateDTO = authorDTOBuilder.builderAuthorDTO();
        Author expectedCreatedAuthor = authorMapper.toModel(expectedAuthorToCreateDTO);

        //when
        when(authorRepository.findByName(expectedAuthorToCreateDTO.getName()))
                .thenReturn(Optional.of(expectedCreatedAuthor));

        assertThrows(AuthorAlreadyExistsException.class,
                ()-> authorService.create(expectedAuthorToCreateDTO));

    }

    @Test
    void whenValidIdIsGivenThenAnAuthorShouldBeReturned() {
        AuthorDTO expectedFoundAuthorDTO = authorDTOBuilder.builderAuthorDTO();
        Author expectedFoundAuthor = authorMapper.toModel(expectedFoundAuthorDTO);

        when(authorRepository.findById(expectedFoundAuthorDTO.getId()))
                .thenReturn(Optional.of(expectedFoundAuthor));

        AuthorDTO foundAuthorDTO = authorService.findById(expectedFoundAuthorDTO.getId());

        assertThat(foundAuthorDTO, is(equalTo(expectedFoundAuthorDTO)));
    }

    @Test
    void whenInvalidIdIsGivenThenAnExceptionShouldBeThrown() {
        AuthorDTO expectedFoundAuthorDTO = authorDTOBuilder.builderAuthorDTO();

        when(authorRepository.findById(expectedFoundAuthorDTO.getId()))
                .thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class,
                ()-> authorService.findById(expectedFoundAuthorDTO.getId()));
    }
}
