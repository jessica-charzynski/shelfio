package com.shelfio.shelfio.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;


    @Column(nullable = false, unique = true)
    private String name;


    @OneToMany(mappedBy = "category")
    private List<Book> books;
}
