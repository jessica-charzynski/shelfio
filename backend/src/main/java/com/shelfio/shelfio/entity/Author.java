package com.shelfio.shelfio.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;


    @Column(nullable = false)
    private String firstName;


    @Column(nullable = false)
    private String lastName;


    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books;
}
