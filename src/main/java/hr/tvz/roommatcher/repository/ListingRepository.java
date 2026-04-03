package hr.tvz.roommatcher.repository;

import hr.tvz.roommatcher.model.entities.Listing;

import java.util.List;
import java.util.Optional;

public interface ListingRepository {
    List<Listing> findAll();
    Optional<Listing> findById(Long id);

}
