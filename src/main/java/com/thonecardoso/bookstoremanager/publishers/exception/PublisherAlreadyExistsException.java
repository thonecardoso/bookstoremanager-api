package com.thonecardoso.bookstoremanager.publishers.exception;

import javax.persistence.EntityExistsException;

public class PublisherAlreadyExistsException extends EntityExistsException {
    public PublisherAlreadyExistsException(String name, String code) {
        super(String.format("User with name %s or code %s already exists", name, code));
    }
}
