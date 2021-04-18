package com.thonecardoso.bookstoremanager.publishers.service;

import com.thonecardoso.bookstoremanager.publishers.dto.PublisherDTO;
import com.thonecardoso.bookstoremanager.publishers.entity.Publisher;
import com.thonecardoso.bookstoremanager.publishers.exception.PublisherAlreadyExistsException;
import com.thonecardoso.bookstoremanager.publishers.exception.PublisherNotFoundException;
import com.thonecardoso.bookstoremanager.publishers.mapper.PublisherMapper;
import com.thonecardoso.bookstoremanager.publishers.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    private final static PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public PublisherDTO create(PublisherDTO publisherDTO){
        verifyIfExists(publisherDTO.getName(), publisherDTO.getCode());
        Publisher publisherToCreate = publisherMapper.toModel(publisherDTO);
        Publisher createdPublisher = publisherRepository.save(publisherToCreate);
        return publisherMapper.toDTO(createdPublisher);
    }

    public PublisherDTO findById(Long id) {
        Publisher foundPublisher = verifyAndGetPublisher(id);
        return publisherMapper.toDTO(foundPublisher);
    }

    public List<PublisherDTO> findAll(){
        return publisherRepository.findAll().stream().map(publisherMapper::toDTO).collect(Collectors.toList());
    }

    private void verifyIfExists(String name, String code) {
        publisherRepository.findByNameOrCode(name, code)
                .ifPresent( publisher -> {
                    throw new PublisherAlreadyExistsException(name, code);
                });
    }

    public void delete(Long id){
        verifyAndGetPublisher(id);
        publisherRepository.deleteById(id);
    }

    private Publisher verifyAndGetPublisher(Long id) {
        return publisherRepository.findById(id).orElseThrow(() -> new PublisherNotFoundException(id));
    }
}
