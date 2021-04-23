package com.thonecardoso.bookstoremanager.books.service;

import com.thonecardoso.bookstoremanager.author.entity.Author;
import com.thonecardoso.bookstoremanager.author.service.AuthorService;
import com.thonecardoso.bookstoremanager.books.builder.BookRequestDTOBuilder;
import com.thonecardoso.bookstoremanager.books.builder.BookResponseDTOBuilder;
import com.thonecardoso.bookstoremanager.books.dto.BookRequestDTO;
import com.thonecardoso.bookstoremanager.books.dto.BookResponseDTO;
import com.thonecardoso.bookstoremanager.books.entity.Book;
import com.thonecardoso.bookstoremanager.books.exception.BookAlreadyExistsException;
import com.thonecardoso.bookstoremanager.books.exception.BookNotFoundException;
import com.thonecardoso.bookstoremanager.books.mapper.BookMapper;
import com.thonecardoso.bookstoremanager.books.repository.BookRepository;
import com.thonecardoso.bookstoremanager.publishers.entity.Publisher;
import com.thonecardoso.bookstoremanager.publishers.service.PublisherService;
import com.thonecardoso.bookstoremanager.users.dto.AuthenticatedUser;
import com.thonecardoso.bookstoremanager.users.entity.User;
import com.thonecardoso.bookstoremanager.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserService userService;

    @Mock
    private AuthorService authorService;

    @Mock
    private PublisherService publisherService;

    @InjectMocks
    private BookService bookService;

    private BookRequestDTOBuilder bookRequestDTOBuilder;

    private BookResponseDTOBuilder bookResponseDTOBuilder;

    private AuthenticatedUser authenticatedUser;

    @BeforeEach
    void setUp() {
        bookRequestDTOBuilder = BookRequestDTOBuilder.builder().build();
        bookResponseDTOBuilder = BookResponseDTOBuilder.builder().build();
        authenticatedUser = new AuthenticatedUser("thonecardoso", "123456", "ADMIN");
    }

    @Test
    void whenNewBookIsInformedThenItShouldBeCreated() {
        BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedCreatedBookDTO = bookResponseDTOBuilder.buildBookResponse();
        Book expectedBookToCreate = bookMapper.toModel(expectedCreatedBookDTO);

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername()))
                .thenReturn(new User());
        when(bookRepository.findByNameAndIsbnAndUser(
                eq(expectedBookToCreateDTO.getName()),
                eq(expectedBookToCreateDTO.getIsbn()),
                any(User.class))).thenReturn(Optional.empty());
        when(publisherService.verifyAndGetPublisherIfExists(expectedBookToCreateDTO.getPublisherId()))
                .thenReturn(new Publisher());
        when(authorService.verifyAndGetAuthorIfExists(expectedBookToCreateDTO.getAuthorId()))
                .thenReturn(new Author());
        when(bookRepository.save(any(Book.class))).thenReturn(expectedBookToCreate);

        BookResponseDTO createdBookResponseDTO = bookService.create(authenticatedUser, expectedBookToCreateDTO);

        assertThat(createdBookResponseDTO, is(equalTo(expectedCreatedBookDTO)));

    }

    @Test
    void whenExistingBookIsInformedToCreatedThenAnExceptionShouldBeThrown() {
        BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedCreatedBookDTO = bookResponseDTOBuilder.buildBookResponse();
        Book expectedBookToCreate = bookMapper.toModel(expectedCreatedBookDTO);

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername()))
                .thenReturn(new User());
        when(bookRepository.findByNameAndIsbnAndUser(
                eq(expectedBookToCreateDTO.getName()),
                eq(expectedBookToCreateDTO.getIsbn()),
                any(User.class))).thenReturn(Optional.of(expectedBookToCreate));

        assertThrows(BookAlreadyExistsException.class, () -> bookService.create(authenticatedUser, expectedBookToCreateDTO));

    }

    @Test
    void whenExistingBookIsInformedThenABookShouldBeReturned() {
        BookRequestDTO expectedBookToFindDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedFoundBookDTO = bookResponseDTOBuilder.buildBookResponse();
        Book expectedFoundBook = bookMapper.toModel(expectedFoundBookDTO);

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername()))
                .thenReturn(new User());

        when(bookRepository.findByIdAndUser(
                eq(expectedBookToFindDTO.getId()),
                any(User.class))).thenReturn(Optional.of(expectedFoundBook));

        BookResponseDTO foundBookDTO = bookService.findByIdAndUser(authenticatedUser, expectedBookToFindDTO.getId());

        assertThat(foundBookDTO, is(equalTo(expectedFoundBookDTO)));

    }

    @Test
    void whenNotExistingBookIsInformedThenAnExceptionShouldBeThrown() {
        BookRequestDTO expectedBookToFindDTO = bookRequestDTOBuilder.buildRequestBookDTO();

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername()))
                .thenReturn(new User());

        when(bookRepository.findByIdAndUser(
                eq(expectedBookToFindDTO.getId()),
                any(User.class))).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class,
                () -> bookService.findByIdAndUser(authenticatedUser, expectedBookToFindDTO.getId()));

    }

    @Test
    void whenListBookIsInformedThenAnEmptyListShouldBeReturned() {

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findAllByUser(any(User.class))).thenReturn(Collections.emptyList());

        List<BookResponseDTO> returnedBooksResponseList = bookService.findAllByUser(authenticatedUser);

        assertThat(returnedBooksResponseList.size(), is(0));
    }

    @Test
    void whenExistingBookIsInformedThenItShouldBeDeleted() {
        BookRequestDTO expectedBookToBeDeletedDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        Book expectedFoundBook = bookMapper.toModel(expectedBookToBeDeletedDTO);
        Long expectedBookIdToBeDeletedDTO = expectedBookToBeDeletedDTO.getId();

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername()))
                .thenReturn(new User());
        when(bookRepository.findByIdAndUser(eq(expectedBookIdToBeDeletedDTO), any(User.class)))
                .thenReturn(Optional.of(expectedFoundBook));
        doNothing().when(bookRepository).deleteByIdAndUser(eq(expectedBookIdToBeDeletedDTO), any(User.class));

        bookService.deleteByIdAndUser(authenticatedUser, expectedBookIdToBeDeletedDTO);

        verify(bookRepository, times(1)).deleteByIdAndUser(eq(expectedBookIdToBeDeletedDTO), any(User.class));
    }

    @Test
    void whenNotExistingBookToDeleteIsInformedThenAnExceptionShouldBeThrown() {
        BookRequestDTO expectedBookToBeDeletedDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        Long expectedBookIdToBeDeletedDTO = expectedBookToBeDeletedDTO.getId();

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername()))
                .thenReturn(new User());
        when(bookRepository.findByIdAndUser(eq(expectedBookIdToBeDeletedDTO), any(User.class)))
                .thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.deleteByIdAndUser(authenticatedUser, expectedBookIdToBeDeletedDTO));
    }
}
