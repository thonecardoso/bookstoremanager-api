package com.thonecardoso.bookstoremanager.authors.service;

import com.thonecardoso.bookstoremanager.authors.mapper.AuthorMapper;
import com.thonecardoso.bookstoremanager.authors.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final static AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
}
