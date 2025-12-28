package com.shelfio.shelfio.repository;

import com.shelfio.shelfio.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CollectionRepository extends JpaRepository<Collection, Long> {
    boolean existsByNameIgnoreCase(String name);

}
