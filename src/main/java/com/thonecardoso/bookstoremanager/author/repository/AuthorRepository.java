package com.thonecardoso.bookstoremanager.author.repository;

import com.thonecardoso.bookstoremanager.author.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);

}
