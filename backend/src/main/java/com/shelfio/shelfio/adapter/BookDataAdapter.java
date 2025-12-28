package com.shelfio.shelfio.adapter;

import com.shelfio.shelfio.dto.ExternalBookDto;

import java.util.Optional;

public interface BookDataAdapter {

    Optional<ExternalBookDto> fetchBookByIsbn(String isbn);
}
