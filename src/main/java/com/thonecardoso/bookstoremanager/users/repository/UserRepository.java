package com.thonecardoso.bookstoremanager.users.repository;

import com.thonecardoso.bookstoremanager.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
