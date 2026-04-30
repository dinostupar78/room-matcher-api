package hr.tvz.roommatematcher.repository;
import hr.tvz.roommatematcher.model.FavoriteListing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FavoriteListingJpaRepository extends JpaRepository<FavoriteListing, Long> {
        boolean existsByAppUserIdAndListingId(Long appUserId, Long listingId);
        List<FavoriteListing> findByAppUserId(Long appUserId);
        void deleteByAppUserIdAndListingId(Long appUserId, Long listingId);
}
