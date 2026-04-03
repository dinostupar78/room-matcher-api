package hr.tvz.roommatcher.controller;

import hr.tvz.roommatcher.dto.ListingDTO;
import hr.tvz.roommatcher.service.ListingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingService listingService;

    @GetMapping
    public ResponseEntity<List<ListingDTO>> getAllListings() {
        List<ListingDTO> listings = listingService.findAll();

        if(listings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(listings);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingDTO> findById(@PathVariable Long id) {
        Optional<ListingDTO> findTaskById = listingService.findById(id);

        if(findTaskById.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(findTaskById.get());
        }

    }
}
