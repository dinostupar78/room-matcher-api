package hr.tvz.roommatcher.service;

import hr.tvz.roommatcher.dto.ListingDTO;
import hr.tvz.roommatcher.dto.ListingRequestDTO;
import hr.tvz.roommatcher.dto.ListingResponseDTO;
import hr.tvz.roommatcher.mapper.ListingMapper;
import hr.tvz.roommatcher.model.Listing;
import hr.tvz.roommatcher.repository.ListingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;
    private final ListingMapper listingMapper;


    @Override
    public List<ListingResponseDTO> findAll() {
        return listingRepository.findAll().stream()
                .map(listingMapper::toListingResponseDTO)
                .toList();
    }

    @Override
    public Optional<ListingResponseDTO> findById(long id) {
        return listingRepository.findById(id)
                .map(listingMapper::toListingResponseDTO);
    }

    @Override
    public Optional<ListingRequestDTO> save(ListingRequestDTO listingRequestDTO) {
        Listing listing = listingMapper.toListing(listingRequestDTO);
        listingRepository.save(listing);
        return Optional.of(listingRequestDTO);
    }

    @Override
    public Optional<ListingResponseDTO> deleteById(long id) {
        Optional<Listing> listingToDelete = listingRepository.findById(id);

        if(listingToDelete.isEmpty()) {
            return Optional.empty();

        } else {
            listingRepository.deleteById(id);
            return Optional.of(listingMapper.toListingResponseDTO(listingToDelete.get()));
        }
    }

}
