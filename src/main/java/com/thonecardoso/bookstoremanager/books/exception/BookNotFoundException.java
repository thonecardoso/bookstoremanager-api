package com.thonecardoso.bookstoremanager.books.exception;

import javax.persistence.EntityNotFoundException;

public class BookNotFoundException extends EntityNotFoundException {
    public BookNotFoundException(Long bookId) {
        super(String.format("Book with ID %s not exists", bookId));
    }

    public BookNotFoundException(Long bookId, String username) {
        super(String.format("Book with ID %s and/or username %s not exists", bookId, username));
    }
}
