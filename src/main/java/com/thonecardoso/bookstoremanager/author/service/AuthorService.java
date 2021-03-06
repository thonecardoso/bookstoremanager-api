package com.thonecardoso.bookstoremanager.author.service;

import com.thonecardoso.bookstoremanager.author.dto.AuthorDTO;
import com.thonecardoso.bookstoremanager.author.entity.Author;
import com.thonecardoso.bookstoremanager.author.exception.AuthorAlreadyExistsException;
import com.thonecardoso.bookstoremanager.author.exception.AuthorNotFoundException;
import com.thonecardoso.bookstoremanager.author.mapper.AuthorMapper;
import com.thonecardoso.bookstoremanager.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final static AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorDTO create(AuthorDTO authorDTO){
        verifyIfExists(authorDTO.getName());
        Author author = authorMapper.toModel(authorDTO);
        Author createdAuthor = authorRepository.save(author);
        return authorMapper.toDTO(createdAuthor);
    }

    public AuthorDTO findById(Long id) {
        Author foundAuthor = verifyAndGetAuthorIfExists(id);
        return authorMapper.toDTO(foundAuthor);
    }

    public List<AuthorDTO> findAll(){
        return authorRepository.findAll().stream().map(authorMapper::toDTO).collect(Collectors.toList());
    }

    private void verifyIfExists(String authorName) {
        authorRepository.findByName(authorName)
                .ifPresent( author -> {
                    throw new AuthorAlreadyExistsException(authorName);
                });
    }

    public void delete(Long id){
        verifyAndGetAuthorIfExists(id);
        authorRepository.deleteById(id);
    }

    public Author verifyAndGetAuthorIfExists(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
    }


}
