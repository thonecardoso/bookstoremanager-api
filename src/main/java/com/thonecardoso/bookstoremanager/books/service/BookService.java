package com.thonecardoso.bookstoremanager.books.service;

import com.thonecardoso.bookstoremanager.author.entity.Author;
import com.thonecardoso.bookstoremanager.author.service.AuthorService;
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
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    private BookRepository bookRepository;

    private UserService userService;

    private AuthorService authorService;

    private PublisherService publisherService;

    public BookResponseDTO create(AuthenticatedUser authenticatedUser, BookRequestDTO bookRequestDTO) {
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        verityIfBookIsAlreadyRegistered(foundAuthenticatedUser, bookRequestDTO);

        Book bookToSave = bookMapper.toModel(bookRequestDTO);

        return saveAndReturnBookResponseDTO(bookRequestDTO, foundAuthenticatedUser, bookToSave);
    }

    public BookResponseDTO findByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId) {
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        return bookRepository.findByIdAndUser(bookId, foundAuthenticatedUser)
                .map(bookMapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    public List<BookResponseDTO> findAllByUser(AuthenticatedUser authenticatedUser) {
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        return bookRepository.findAllByUser(foundAuthenticatedUser)
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId) {
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        Book foundBookToDelete = verifyAndGetIfExists(bookId, foundAuthenticatedUser);
        bookRepository.deleteByIdAndUser(foundBookToDelete.getId(), foundAuthenticatedUser);
    }

    public BookResponseDTO updateByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId, BookRequestDTO bookRequestDTO){
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        Book foundBook = verifyAndGetIfExists(bookId, foundAuthenticatedUser);

        Book bookToUpdate = bookMapper.toModel(bookRequestDTO);
        bookToUpdate.setId(foundBook.getId());
        bookToUpdate.setCreatedDate(foundBook.getCreatedDate());

        return saveAndReturnBookResponseDTO(bookRequestDTO, foundAuthenticatedUser, bookToUpdate);
    }

    private BookResponseDTO saveAndReturnBookResponseDTO(BookRequestDTO bookRequestDTO, User foundAuthenticatedUser, Book bookToSave) {
        Publisher foundPublisher = publisherService.verifyAndGetPublisherIfExists(bookRequestDTO.getPublisherId());
        Author foundAuthor = authorService.verifyAndGetAuthorIfExists(bookRequestDTO.getAuthorId());
        bookToSave.setAuthor(foundAuthor);
        bookToSave.setPublisher(foundPublisher);
        bookToSave.setUser(foundAuthenticatedUser);
        Book savedBook = bookRepository.save(bookToSave);
        return bookMapper.toDTO(savedBook);
    }

    private Book verifyAndGetIfExists(Long bookId, User foundAuthenticatedUser) {
        return bookRepository.findByIdAndUser(bookId, foundAuthenticatedUser)
                .orElseThrow(() -> new BookNotFoundException(bookId, foundAuthenticatedUser.getUsername()));
    }


    private void verityIfBookIsAlreadyRegistered(User foundUser, BookRequestDTO bookRequestDTO) {
        bookRepository.findByNameAndIsbnAndUser(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundUser)
                .ifPresent(duplicatedBook -> {
                    throw new BookAlreadyExistsException(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundUser.getUsername());
                });
    }

}