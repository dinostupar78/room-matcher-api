package hr.tvz.roommatcher.repository;

import hr.tvz.roommatcher.model.ListingImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingImageJpaRepository extends JpaRepository<ListingImage, Long> {
    List<ListingImage> findByListingId(Long listingId);

}
