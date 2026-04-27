package hr.tvz.roommatcher.repository;
import hr.tvz.roommatcher.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ListingJpaRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByAppUserId(Long appUserId);

}
