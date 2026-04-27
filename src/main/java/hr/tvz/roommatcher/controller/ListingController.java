package hr.tvz.roommatcher.controller;

import hr.tvz.roommatcher.dto.listing.ListingRequest;
import hr.tvz.roommatcher.dto.listing.ListingResponse;
import hr.tvz.roommatcher.dto.listing.UpdateListingRequest;
import hr.tvz.roommatcher.service.ListingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingService listingService;

    @GetMapping
    public ResponseEntity<List<ListingResponse>> getAllListings() {
        List<ListingResponse> listings = listingService.findAll();

        if(listings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(listings);
        }

    }

    @GetMapping("/me")
    public ResponseEntity<List<ListingResponse>> getMyListings() {
        List<ListingResponse> myListings = listingService.getMyListings();

        if (myListings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(myListings);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingResponse> findById(@PathVariable Long id) {
        Optional<ListingResponse> findTaskById = listingService.findById(id);

        if(findTaskById.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(findTaskById.get());
        }

    }

    @PostMapping("/{id}/images")
    public ResponseEntity<ListingResponse> uploadListingImage(@PathVariable Long id, @RequestParam("files") MultipartFile file) {
        ListingResponse updatedListing = listingService.uploadImage(id, file);

        return ResponseEntity.status(HttpStatus.OK).body(updatedListing);
    }

    @DeleteMapping("/{id}/images/{imageId}")
    public ResponseEntity<Void> deleteListingImage(@PathVariable Long id, @PathVariable Long imageId) {
        listingService.deleteImage(id, imageId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ListingResponse> createListing(@Valid @RequestBody ListingRequest listingRequest) {
        ListingResponse createdListing = listingService.addListing(listingRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdListing);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ListingResponse> updateListing(@PathVariable Long id, @Valid @RequestBody UpdateListingRequest updateListingRequest) {
        Optional<ListingResponse> listingToUpdate = listingService.findById(id);

        if(listingToUpdate.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            ListingResponse updatedListing = listingService.updateListing(id, updateListingRequest);
            return ResponseEntity.status(HttpStatus.OK).body(updatedListing);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        listingService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

}