package com.shelfio.shelfio.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Entity
@Table(name = "reading_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadingStatus {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long readingStatusId;


    @Column(nullable = false, unique = true)
    private String status;


    @OneToMany(mappedBy = "readingStatus")
    private List<Book> books;
}
