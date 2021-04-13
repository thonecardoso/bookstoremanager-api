package com.thonecardoso.bookstoremanager.publishers.repository;

import com.thonecardoso.bookstoremanager.publishers.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
