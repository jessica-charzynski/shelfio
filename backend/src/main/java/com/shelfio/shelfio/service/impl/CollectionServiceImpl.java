package com.shelfio.shelfio.service.impl;

import com.shelfio.shelfio.dto.response.CollectionResponseDto;
import com.shelfio.shelfio.entity.Collection;
import com.shelfio.shelfio.entity.Book;
import com.shelfio.shelfio.exception.InvalidInputException;
import com.shelfio.shelfio.exception.ResourceAlreadyExistsException;
import com.shelfio.shelfio.exception.ResourceNotFoundException;
import com.shelfio.shelfio.mapper.CollectionMapper;
import com.shelfio.shelfio.repository.BookRepository;
import com.shelfio.shelfio.repository.CollectionRepository;
import com.shelfio.shelfio.service.CollectionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;
    private final BookRepository bookRepository;

    @Override
    public CollectionResponseDto createCollection(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Collection name cannot be null or empty");
        }

        if (collectionRepository.existsByNameIgnoreCase(name)) {
            throw new ResourceAlreadyExistsException(
                    "Collection with name '" + name + "' already exists");
        }

        Collection collection = new Collection();
        collection.setName(name);

        Collection savedCollection = collectionRepository.save(collection);
        log.info("Created collection with ID: {} and name: {}",
                savedCollection.getCollectionId(), name);

        return CollectionMapper.toDto(savedCollection);
    }

    @Override
    public List<CollectionResponseDto> getAllCollections() {
        List<Collection> collections = collectionRepository.findAll();
        log.info("Retrieved {} collections", collections.size());

        return collections.stream()
                .map(CollectionMapper::toDto)
                .toList();
    }

    @Override
    public CollectionResponseDto getCollectionById(Long id) {
        if (id == null) {
            throw new InvalidInputException("Collection ID cannot be null");
        }

        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Collection not found with ID: " + id));

        log.info("Retrieved collection with ID: {}", id);
        return CollectionMapper.toDto(collection);
    }

    @Override
    public void addBookToCollection(Long collectionId, Long bookId) {
        if (collectionId == null) {
            throw new InvalidInputException("Collection ID cannot be null");
        }
        if (bookId == null) {
            throw new InvalidInputException("Book ID cannot be null");
        }

        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Collection not found with ID: " + collectionId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with ID: " + bookId));

        if (collection.getBooks().contains(book)) {
            log.info("Book ID: {} already exists in collection ID: {}", bookId, collectionId);
            return;
        }

        collection.getBooks().add(book);
        collectionRepository.save(collection);
        log.info("Added book ID: {} to collection ID: {}", bookId, collectionId);
    }

    @Override
    public void removeBookFromCollection(Long collectionId, Long bookId) {
        if (collectionId == null) {
            throw new InvalidInputException("Collection ID cannot be null");
        }
        if (bookId == null) {
            throw new InvalidInputException("Book ID cannot be null");
        }

        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Collection not found with ID: " + collectionId));

        boolean removed = collection.getBooks().removeIf(
                book -> book.getBookId().equals(bookId));

        if (removed) {
            collectionRepository.save(collection);
            log.info("Removed book ID: {} from collection ID: {}", bookId, collectionId);
        } else {
            log.info("Book ID: {} not found in collection ID: {}", bookId, collectionId);
        }
    }

    @Override
    public void deleteCollection(Long id) {
        if (id == null) {
            throw new InvalidInputException("Collection ID cannot be null");
        }

        if (!collectionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Collection not found with ID: " + id);
        }

        collectionRepository.deleteById(id);
        log.info("Deleted collection with ID: {}", id);
    }
}