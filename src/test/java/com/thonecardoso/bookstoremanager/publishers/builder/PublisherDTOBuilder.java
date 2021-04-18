package com.thonecardoso.bookstoremanager.publishers.builder;

import com.thonecardoso.bookstoremanager.publishers.dto.PublisherDTO;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class PublisherDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String name = "Cardoso Editoras";

    @Builder.Default
    private final String code = "Cardoso12333";

    @Builder.Default
    private final LocalDate foundationDate = LocalDate.of(2021,7,1);

    public PublisherDTO builderPublisherDTO() {
        return new PublisherDTO(id, name, code, foundationDate);
    }
}
