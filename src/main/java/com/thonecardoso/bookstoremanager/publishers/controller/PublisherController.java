package com.thonecardoso.bookstoremanager.publishers.controller;

import com.thonecardoso.bookstoremanager.publishers.dto.PublisherDTO;
import com.thonecardoso.bookstoremanager.publishers.repository.PublisherRepository;
import com.thonecardoso.bookstoremanager.publishers.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
public class PublisherController implements PublisherControllerDocs {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PublisherDTO create(@Valid @RequestBody PublisherDTO publisherDTO) {
        return publisherService.create(publisherDTO);
    }

    @GetMapping("/{id}")
    public PublisherDTO findById(@PathVariable Long id) {
        return publisherService.findById(id);
    }

    @GetMapping
    public List<PublisherDTO> findAll() {
        return publisherService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        publisherService.delete(id);
    }
}
