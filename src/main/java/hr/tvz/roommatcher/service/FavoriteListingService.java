package hr.tvz.roommatcher.service;
import hr.tvz.roommatcher.dto.listing.ListingResponse;
import java.util.List;

public interface FavoriteListingService {
    void addToFavorites(Long listingId);
    void removeFromFavorites(Long listingId);
    List<ListingResponse> getMyFavorites();
    boolean isFavorite(Long listingId);
}
