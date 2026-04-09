package hr.tvz.roommatcher.controller;

import hr.tvz.roommatcher.dto.ListingDTO;
import hr.tvz.roommatcher.dto.ListingRequestDTO;
import hr.tvz.roommatcher.dto.ListingResponseDTO;
import hr.tvz.roommatcher.service.ListingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingService listingService;

    @GetMapping
    public ResponseEntity<List<ListingResponseDTO>> getAllListings() {
        List<ListingResponseDTO> listings = listingService.findAll();

        if(listings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(listings);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingResponseDTO> findById(@PathVariable Long id) {
        Optional<ListingResponseDTO> findTaskById = listingService.findById(id);

        if(findTaskById.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(findTaskById.get());
        }

    }

    @PostMapping
    public ResponseEntity<ListingRequestDTO> createListing(@Valid @RequestBody ListingRequestDTO listingRequestDTO) {
        Optional<ListingRequestDTO> createdListing = listingService.save(listingRequestDTO);

        if(createdListing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdListing.get());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        Optional<ListingResponseDTO> listingToDelete = listingService.findById(id);

        if(listingToDelete.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            listingService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

}