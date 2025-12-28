package com.shelfio.shelfio.service;

import com.shelfio.shelfio.dto.response.CollectionResponseDto;
import java.util.List;

public interface CollectionService {

    CollectionResponseDto createCollection(String name);

    List<CollectionResponseDto> getAllCollections();

    CollectionResponseDto getCollectionById(Long id);

    void addBookToCollection(Long collectionId, Long bookId);

    void removeBookFromCollection(Long collectionId, Long bookId);

    void deleteCollection(Long id);
}