package com.shelfio.shelfio.config;

import com.shelfio.shelfio.entity.Collection;
import com.shelfio.shelfio.repository.CollectionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CollectionDataInitializer {

    private final CollectionRepository collectionRepository;

    @PostConstruct
    public void initFavoritesCollection() {

        boolean exists =
                collectionRepository.existsByNameIgnoreCase("Favorites");

        if (!exists) {
            Collection favorites = new Collection();
            favorites.setName("Favorites");

            collectionRepository.save(favorites);
        }
    }
}
