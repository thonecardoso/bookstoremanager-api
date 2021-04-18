package com.thonecardoso.bookstoremanager.publishers.repository;

import com.thonecardoso.bookstoremanager.publishers.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Optional<Object> findByNameOrCode(String name, String code);
}
