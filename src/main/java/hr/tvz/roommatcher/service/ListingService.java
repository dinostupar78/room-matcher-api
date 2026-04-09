package hr.tvz.roommatcher.service;

import hr.tvz.roommatcher.dto.ListingDTO;
import hr.tvz.roommatcher.dto.ListingRequestDTO;
import hr.tvz.roommatcher.dto.ListingResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ListingService {
    List<ListingResponseDTO> findAll();
    Optional<ListingResponseDTO> findById(long id);
    Optional<ListingRequestDTO> save(ListingRequestDTO listingRequestDTO);
}
