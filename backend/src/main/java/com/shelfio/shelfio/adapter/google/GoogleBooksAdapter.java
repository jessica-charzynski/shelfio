package com.shelfio.shelfio.adapter.google;

import com.shelfio.shelfio.adapter.BookDataAdapter;
import com.shelfio.shelfio.dto.ExternalBookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class GoogleBooksAdapter implements BookDataAdapter {
    private static final String GOOGLE_BOOKS_API =
            "https://www.googleapis.com/books/v1/volumes?q=isbn:";

    private final RestTemplate restTemplate = new RestTemplate();

    private List<String> extractCategories(Map<String, Object> volumeInfo) {

        Object rawCategories = volumeInfo.get("categories");

        if (rawCategories instanceof List<?> list) {
            return list.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .toList();
        }

        return List.of();
    }


    @Override
    public Optional<ExternalBookDto> fetchBookByIsbn(String isbn) {

        String url = GOOGLE_BOOKS_API + isbn;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !response.containsKey("items")) {
            return Optional.empty();
        }

        List<Map<String, Object>> items =
                (List<Map<String, Object>>) response.get("items");

        if (items.isEmpty()) {
            return Optional.empty();
        }

        Map<String, Object> volumeInfo =
                (Map<String, Object>) items.get(0).get("volumeInfo");

        String title = (String) volumeInfo.get("title");
        String publisher = (String) volumeInfo.get("publisher");
        Integer pageCount = (Integer) volumeInfo.get("pageCount");
        List<String> categories = extractCategories(volumeInfo);


        // Authors
        List<String> authors = (List<String>) volumeInfo.get("authors");
        String firstName = "Unknown";
        String lastName = "Author";

        if (authors != null && !authors.isEmpty()) {
            String[] parts = authors.get(0).split(" ", 2);
            firstName = parts[0];
            if (parts.length > 1) {
                lastName = parts[1];
            }
        }

        // Cover image
        String coverUrl = null;
        if (volumeInfo.containsKey("imageLinks")) {
            Map<String, String> imageLinks =
                    (Map<String, String>) volumeInfo.get("imageLinks");
            coverUrl = imageLinks.get("thumbnail");
        }

        return Optional.of(
                ExternalBookDto.builder()
                        .title(title)
                        .authorFirstName(firstName)
                        .authorLastName(lastName)
                        .isbn(isbn)
                        .pages(pageCount)
                        .publisher(publisher)
                        .coverUrl(coverUrl)
                        .categories(categories)
                        .build()
        );
    }
}
