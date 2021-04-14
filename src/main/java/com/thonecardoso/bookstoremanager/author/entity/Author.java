package com.thonecardoso.bookstoremanager.author.entity;

import com.thonecardoso.bookstoremanager.books.entity.Book;
import com.thonecardoso.bookstoremanager.entity.Auditable;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Author extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "integer default 0")
    private int age;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Book> books;
}
