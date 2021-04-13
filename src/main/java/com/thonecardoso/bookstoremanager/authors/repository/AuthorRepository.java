package com.thonecardoso.bookstoremanager.authors.repository;

import com.thonecardoso.bookstoremanager.authors.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
