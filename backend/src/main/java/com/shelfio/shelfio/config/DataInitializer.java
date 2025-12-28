package com.shelfio.shelfio.config;

import com.shelfio.shelfio.entity.ReadingStatus;
import com.shelfio.shelfio.repository.ReadingStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;


import java.util.List;


@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {


    private final ReadingStatusRepository readingStatusRepository;


    @Override
    public void run(String... args) {


        List<String> defaultStatuses = List.of(
                "Not started",
                "Reading",
                "Finished"
        );


        for (String status : defaultStatuses) {
            readingStatusRepository.findByStatus(status)
                    .orElseGet(() -> readingStatusRepository.save(
                            ReadingStatus.builder()
                                    .status(status)
                                    .build()
                    ));
        }
    }
}