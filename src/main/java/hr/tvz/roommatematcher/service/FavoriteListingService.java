package hr.tvz.roommatematcher.service;
import hr.tvz.roommatematcher.dto.listing.ListingResponse;
import java.util.List;

public interface FavoriteListingService {
    void addToFavorites(Long listingId);
    void removeFromFavorites(Long listingId);
    List<ListingResponse> getMyFavorites();
    boolean isFavorite(Long listingId);
}
