package com.thonecardoso.bookstoremanager.books.repository;

import com.thonecardoso.bookstoremanager.books.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
