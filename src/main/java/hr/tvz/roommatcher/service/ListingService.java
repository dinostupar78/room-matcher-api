package hr.tvz.roommatcher.service;

import hr.tvz.roommatcher.dto.ListingDTO;

import java.util.List;
import java.util.Optional;

public interface ListingService {
    List<ListingDTO> findAll();
    Optional<ListingDTO> findById(long id);
}
