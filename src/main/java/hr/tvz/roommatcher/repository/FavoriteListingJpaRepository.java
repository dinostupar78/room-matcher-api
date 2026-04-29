package hr.tvz.roommatcher.repository;
import hr.tvz.roommatcher.model.FavoriteListing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FavoriteListingJpaRepository extends JpaRepository<FavoriteListing, Long> {
        boolean existsByAppUserIdAndListingId(Long appUserId, Long listingId);
        List<FavoriteListing> findByAppUserId(Long appUserId);
        void deleteByAppUserIdAndListingId(Long appUserId, Long listingId);
}
