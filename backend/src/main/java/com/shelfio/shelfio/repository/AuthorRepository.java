package com.shelfio.shelfio.repository;

import com.shelfio.shelfio.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(
            String firstName,
            String lastName
    );
}
