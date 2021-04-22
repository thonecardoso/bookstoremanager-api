package com.thonecardoso.bookstoremanager.books.service;

import com.thonecardoso.bookstoremanager.author.service.AuthorService;
import com.thonecardoso.bookstoremanager.books.mapper.BookMapper;
import com.thonecardoso.bookstoremanager.books.repository.BookRepository;
import com.thonecardoso.bookstoremanager.publishers.entity.Publisher;
import com.thonecardoso.bookstoremanager.publishers.service.PublisherService;
import com.thonecardoso.bookstoremanager.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    private BookRepository bookRepository;

    private UserService userService;

    private AuthorService authorService;

    private PublisherService publisherService;


}