package com.shelfio.shelfio.controller;

import com.shelfio.shelfio.dto.request.CreateCollectionRequest;
import com.shelfio.shelfio.dto.response.ApiResponse;
import com.shelfio.shelfio.dto.response.CollectionResponseDto;
import com.shelfio.shelfio.service.CollectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/collections")
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;

    @PostMapping
    public ResponseEntity<ApiResponse<CollectionResponseDto>> createCollection(
            @Valid @RequestBody CreateCollectionRequest request) {

        log.info("Request to create collection with name: {}", request.getName());
        CollectionResponseDto collection = collectionService.createCollection(request.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Collection created successfully", collection));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CollectionResponseDto>>> getAllCollections() {
        log.info("Request to get all collections");
        List<CollectionResponseDto> collections = collectionService.getAllCollections();
        return ResponseEntity.ok(ApiResponse.success(collections));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CollectionResponseDto>> getCollection(@PathVariable Long id) {
        log.info("Request to get collection with ID: {}", id);
        CollectionResponseDto collection = collectionService.getCollectionById(id);
        return ResponseEntity.ok(ApiResponse.success(collection));
    }

    @PostMapping("/{collectionId}/books/{bookId}")
    public ResponseEntity<ApiResponse<Void>> addBookToCollection(
            @PathVariable Long collectionId,
            @PathVariable Long bookId) {

        log.info("Request to add book ID: {} to collection ID: {}", bookId, collectionId);
        collectionService.addBookToCollection(collectionId, bookId);
        return ResponseEntity.ok(ApiResponse.success("Book added to collection successfully", null));
    }

    @DeleteMapping("/{collectionId}/books/{bookId}")
    public ResponseEntity<ApiResponse<Void>> removeBookFromCollection(
            @PathVariable Long collectionId,
            @PathVariable Long bookId) {

        log.info("Request to remove book ID: {} from collection ID: {}", bookId, collectionId);
        collectionService.removeBookFromCollection(collectionId, bookId);
        return ResponseEntity.ok(ApiResponse.success("Book removed from collection successfully", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCollection(@PathVariable Long id) {
        log.info("Request to delete collection with ID: {}", id);
        collectionService.deleteCollection(id);
        return ResponseEntity.ok(ApiResponse.success("Collection deleted successfully", null));
    }
}