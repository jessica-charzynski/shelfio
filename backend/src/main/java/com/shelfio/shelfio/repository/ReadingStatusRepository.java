package com.shelfio.shelfio.repository;

import com.shelfio.shelfio.entity.ReadingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface ReadingStatusRepository extends JpaRepository<ReadingStatus, Long> {


    Optional<ReadingStatus> findByStatus(String status);
}
