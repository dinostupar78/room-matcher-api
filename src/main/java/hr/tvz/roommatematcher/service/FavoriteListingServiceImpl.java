package hr.tvz.roommatematcher.service;
import hr.tvz.roommatematcher.dto.listing.ListingResponse;
import hr.tvz.roommatematcher.mapper.ListingMapper;
import hr.tvz.roommatematcher.model.AppUser;
import hr.tvz.roommatematcher.model.FavoriteListing;
import hr.tvz.roommatematcher.model.Listing;
import hr.tvz.roommatematcher.repository.AppUserJpaRepository;
import hr.tvz.roommatematcher.repository.FavoriteListingJpaRepository;
import hr.tvz.roommatematcher.repository.ListingJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteListingServiceImpl implements FavoriteListingService {

    private final FavoriteListingJpaRepository favoriteListingRepository;
    private final ListingJpaRepository listingRepository;
    private final AppUserJpaRepository appUserRepository;
    private final ListingMapper listingMapper;

    @Override
    @Transactional
    public void addToFavorites(Long listingId) {
        AppUser currentUser = getCurrentUser();

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        if (listing.getAppUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You cannot favorite your own listing");
        }

        if (favoriteListingRepository.existsByAppUserIdAndListingId(currentUser.getId(), listingId)) {
            throw new RuntimeException("Listing already in favorites");
        }

        FavoriteListing favoriteListing = FavoriteListing.builder()
                .appUser(currentUser)
                .listing(listing)
                .build();

        favoriteListingRepository.save(favoriteListing);

    }

    @Override
    @Transactional
    public void removeFromFavorites(Long listingId) {
        AppUser currentUser = getCurrentUser();

        favoriteListingRepository.deleteByAppUserIdAndListingId(
                currentUser.getId(),
                listingId
        );

    }

    @Override
    public List<ListingResponse> getMyFavorites() {
        AppUser currentUser = getCurrentUser();

        return favoriteListingRepository.findByAppUserId(currentUser.getId())
                .stream()
                .map(FavoriteListing::getListing)
                .map(listingMapper::toListingResponse)
                .toList();
    }

    @Override
    public boolean isFavorite(Long listingId) {
        AppUser currentUser = getCurrentUser();

        return favoriteListingRepository.existsByAppUserIdAndListingId(
                currentUser.getId(),
                listingId
        );
    }

    private AppUser getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
