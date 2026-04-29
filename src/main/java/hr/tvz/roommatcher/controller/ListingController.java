package hr.tvz.roommatcher.controller;
import hr.tvz.roommatcher.dto.listing.ListingRequest;
import hr.tvz.roommatcher.dto.listing.ListingResponse;
import hr.tvz.roommatcher.dto.listing.UpdateListingRequest;
import hr.tvz.roommatcher.service.ListingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingService listingService;

    @GetMapping
    public ResponseEntity<List<ListingResponse>> getAllListings() {
        return ResponseEntity.ok(listingService.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingResponse> findById(@PathVariable Long id) {
        return listingService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/me")
    public ResponseEntity<List<ListingResponse>> getMyListings() {
        return ResponseEntity.ok(listingService.getMyListings());

    }

    @PostMapping
    public ResponseEntity<ListingResponse> createListing(@Valid @RequestBody ListingRequest listingRequest) {
        ListingResponse createdListing = listingService.addListing(listingRequest);

        URI location = URI.create("/api/listings/" + createdListing.id());

        return ResponseEntity
                .created(location)
                .body(createdListing);

    }

    @PostMapping("/{id}/images")
    public ResponseEntity<ListingResponse> uploadListingImage(@PathVariable Long id, @RequestParam("files") List<MultipartFile> files) {
        return ResponseEntity.ok(listingService.uploadImages(id, files));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ListingResponse> updateListing(@PathVariable Long id, @Valid @RequestBody UpdateListingRequest updateListingRequest) {
        return ResponseEntity.ok(listingService.updateListing(id, updateListingRequest));

    }

    @DeleteMapping("/{id}/images/{imageId}")
    public ResponseEntity<Void> deleteListingImage(@PathVariable Long id, @PathVariable Long imageId) {
        listingService.deleteImage(id, imageId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        listingService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

}