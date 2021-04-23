package com.thonecardoso.bookstoremanager.books.repository;

import com.thonecardoso.bookstoremanager.books.entity.Book;
import com.thonecardoso.bookstoremanager.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByNameAndIsbnAndUser(String name, String isbn, User user);

    Optional<Book> findByIdAndUser(Long bookId, User foundAuthenticatedUser);
}
