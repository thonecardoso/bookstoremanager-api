package com.thonecardoso.bookstoremanager.author.repository;

import com.thonecardoso.bookstoremanager.author.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
