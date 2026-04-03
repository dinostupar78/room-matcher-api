package hr.tvz.roommatcher.service;

import hr.tvz.roommatcher.dto.ListingDTO;
import hr.tvz.roommatcher.model.entities.Listing;
import hr.tvz.roommatcher.repository.ListingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;
    @Override
    public List<ListingDTO> findAll() {
        return listingRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public Optional<ListingDTO> findById(long id) {
        return listingRepository.findById(id)
                .stream()
                .map(this::mapToDTO)
                .findFirst();

    }

    private ListingDTO mapToDTO(Listing listing) {
        return ListingDTO.builder()
                .title(listing.getTitle())
                .address(listing.getAddress())
                .price(listing.getPrice())
                .size(listing.getSize())
                .roomCount(listing.getRoomCount())
                .description(listing.getDescription())
                .availableFrom(listing.getAvailableFrom())
                .isActive(listing.getIsActive())
                .build();
    }
}
