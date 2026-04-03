package hr.tvz.roommatcher.repository;

import hr.tvz.roommatcher.model.entities.Listing;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MockListingRepository implements ListingRepository {

    private static final List<Listing> listings = new ArrayList<>();

    static {
        Listing firstListing = Listing.builder()
                .id(1L)
                .title("Spacious apartment in the city center")
                .address("123 Main St, Cityville")
                .price("$1200/month")
                .size(80)
                .roomCount(3)
                .description("A spacious and modern apartment located in the heart of the city.")
                .availableFrom(LocalDate.now().plusDays(7))
                .isActive(true)
                .build();

        Listing secondListing =Listing.builder()
                        .id(2L)
                        .title("Cozy studio near the university")
                        .address("456 University Ave, Cityville")
                        .price("$800/month")
                        .size(40)
                        .roomCount(1)
                        .description("A cozy and affordable studio apartment located near the university.")
                        .availableFrom(LocalDate.now().plusDays(14))
                        .isActive(true)
                        .build();

        Listing thirdListing = Listing.builder()
                        .id(3L)
                        .title("Modern loft with great views")
                        .address("789 Skyline Blvd, Cityville")
                        .price("$1500/month")
                        .size(100)
                        .roomCount(2)
                        .description("A modern loft apartment with stunning views of the city skyline.")
                        .availableFrom(LocalDate.now().plusDays(30))
                        .isActive(true)
                        .build();

        listings.add(firstListing);
        listings.add(secondListing);
        listings.add(thirdListing);

    }

    @Override
    public List<Listing> findAll() {
        return listings;
    }

    @Override
    public Optional<Listing> findById(Long id) {
        return listings.stream()
                .filter(listing -> listing.getId().equals(id))
                .findFirst();
    }
}
