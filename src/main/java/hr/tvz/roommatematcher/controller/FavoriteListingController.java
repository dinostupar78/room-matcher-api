package hr.tvz.roommatematcher.controller;
import hr.tvz.roommatematcher.dto.listing.ListingResponse;
import hr.tvz.roommatematcher.service.FavoriteListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoriteListingController {

    private final FavoriteListingService favoriteListingService;

    @GetMapping("/me")
    public ResponseEntity<List<ListingResponse>> getMyFavoriteListings() {
        return ResponseEntity.ok(favoriteListingService.getMyFavorites());

    }

    @GetMapping("/{listingId}/exists")
    public ResponseEntity<Boolean> isFavorite(@PathVariable Long listingId) {
        return ResponseEntity.ok(favoriteListingService.isFavorite(listingId));

    }

    @PostMapping("/{listingId}")
    public ResponseEntity<Void> addFavoriteListing(@PathVariable Long listingId) {
        favoriteListingService.addToFavorites(listingId);

        URI location = URI.create("/api/favorites/" + listingId);

        return ResponseEntity.created(location).build();

    }

    @DeleteMapping("/{listingId}")
    public ResponseEntity<Void> removeFromFavorites(@PathVariable Long listingId) {
        favoriteListingService.removeFromFavorites(listingId);
        return ResponseEntity.noContent().build();
    }

}
