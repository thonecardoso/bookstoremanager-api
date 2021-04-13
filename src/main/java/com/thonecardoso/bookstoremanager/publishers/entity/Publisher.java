package com.thonecardoso.bookstoremanager.publishers.entity;

import com.thonecardoso.bookstoremanager.books.entity.Book;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private Date foundationDate;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)
    private List<Book> books;
}
