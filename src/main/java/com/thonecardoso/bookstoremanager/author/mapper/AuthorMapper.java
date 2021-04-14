package com.thonecardoso.bookstoremanager.author.mapper;

import com.thonecardoso.bookstoremanager.author.dto.AuthorDTO;
import com.thonecardoso.bookstoremanager.author.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toModel(AuthorDTO authorDTO);

    AuthorDTO toDTO(Author author);
}
