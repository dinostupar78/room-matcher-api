package hr.tvz.roommatcher.repository;

import hr.tvz.roommatcher.model.Listing;

import java.util.List;
import java.util.Optional;

public interface ListingRepository {
    List<Listing> findAll();
    Optional<Listing> findById(Long id);
    Optional<Listing> save(Listing listing);
    Optional<Listing> deleteById(Long id);
}
