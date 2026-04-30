package hr.tvz.roommatematcher.repository;
import hr.tvz.roommatematcher.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ListingJpaRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByAppUserId(Long appUserId);

}
