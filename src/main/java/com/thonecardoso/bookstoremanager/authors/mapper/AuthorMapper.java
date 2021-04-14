package com.thonecardoso.bookstoremanager.authors.mapper;

import com.thonecardoso.bookstoremanager.authors.dto.AuthorDTO;
import com.thonecardoso.bookstoremanager.authors.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toModel(AuthorDTO authorDTO);

    AuthorDTO toDTO(Author author);
}
