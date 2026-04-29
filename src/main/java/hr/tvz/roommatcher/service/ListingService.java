package hr.tvz.roommatcher.service;
import hr.tvz.roommatcher.dto.listing.ListingRequest;
import hr.tvz.roommatcher.dto.listing.ListingResponse;
import hr.tvz.roommatcher.dto.listing.UpdateListingRequest;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

public interface ListingService {
    List<ListingResponse> findAll();
    Optional<ListingResponse> findById(long id);
    List<ListingResponse> getMyListings();
    ListingResponse addListing(ListingRequest listingRequest);
    ListingResponse updateListing(Long id, UpdateListingRequest updateListingRequest);
    ListingResponse uploadImages(Long listingId, List<MultipartFile> files);
    void deleteImage(Long listingId, Long imageId);
    void deleteById(long id);
}
