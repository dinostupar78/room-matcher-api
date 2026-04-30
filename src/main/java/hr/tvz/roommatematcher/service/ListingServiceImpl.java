package hr.tvz.roommatematcher.service;
import hr.tvz.roommatematcher.dto.listing.ListingRequest;
import hr.tvz.roommatematcher.dto.listing.ListingResponse;
import hr.tvz.roommatematcher.dto.listing.UpdateListingRequest;
import hr.tvz.roommatematcher.mapper.ListingMapper;
import hr.tvz.roommatematcher.model.AppUser;
import hr.tvz.roommatematcher.model.Listing;
import hr.tvz.roommatematcher.model.ListingImage;
import hr.tvz.roommatematcher.repository.AppUserJpaRepository;
import hr.tvz.roommatematcher.repository.ListingImageJpaRepository;
import hr.tvz.roommatematcher.repository.ListingJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ListingServiceImpl implements ListingService {

    private static final String UPLOAD_DIR = "uploads/listings/";

    private final ListingJpaRepository listingRepository;
    private final AppUserJpaRepository appUserRepository;
    private final ListingImageJpaRepository listingImageRepository;
    private final ListingMapper listingMapper;


    @Override
    public List<ListingResponse> findAll() {
        return listingRepository.findAll()
                .stream()
                .map(listingMapper::toListingResponse)
                .toList();
    }

    @Override
    public Optional<ListingResponse> findById(long id) {
        return listingRepository.findById(id)
                .map(listingMapper::toListingResponse);
    }

    @Override
    public List<ListingResponse> getMyListings() {
        AppUser currentUser = getCurrentUser();

        return listingRepository.findByAppUserId(currentUser.getId())
                .stream()
                .map(listingMapper::toListingResponse)
                .toList();
    }

    @Override
    public ListingResponse addListing(ListingRequest listingRequest) {
        AppUser currentUser = getCurrentUser();

        Listing listingToAdd = listingMapper.toListing(listingRequest);
        listingToAdd.setAppUser(currentUser);


        Listing addedListing = listingRepository.save(listingToAdd);
        return listingMapper.toListingResponse(addedListing);

    }

    @Override
    @Transactional
    public ListingResponse updateListing(Long id, UpdateListingRequest updateListingRequest) {
        AppUser currentUser = getCurrentUser();

        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Listing with id " + id + " not found."));

        validateOwner(listing, currentUser);

        listingMapper.updateListingFromRequest(updateListingRequest, listing);

        return listingMapper.toListingResponse(listingRepository.save(listing));

    }

    @Override
    @Transactional
    public ListingResponse uploadImages(Long listingId, List<MultipartFile> files) {
        AppUser currentUser = getCurrentUser();

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        validateOwner(listing, currentUser);

        if (files == null || files.isEmpty()) {
            throw new RuntimeException("Files are empty");
        }

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            Files.createDirectories(uploadPath);

            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }

                String fileName = listingId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                ListingImage image = ListingImage.builder()
                        .imageUrl("/uploads/listings/" + fileName)
                        .listing(listing)
                        .build();

                listingImageRepository.save(image);
            }

            Listing updatedListing = listingRepository.findById(listingId)
                    .orElseThrow(() -> new RuntimeException("Listing not found"));

            return listingMapper.toListingResponse(updatedListing);

        } catch (IOException e) {
            throw new RuntimeException("Could not upload listing images", e);
        }

    }

    @Override
    @Transactional
    public void deleteImage(Long listingId, Long imageId) {
        AppUser currentUser = getCurrentUser();

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        validateOwner(listing, currentUser);

        ListingImage image = listingImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        if (!image.getListing().getId().equals(listingId)) {
            throw new RuntimeException("Image does not belong to this listing");
        }

        try {
            Path path = Paths.get(image.getImageUrl().replaceFirst("^/uploads/", "uploads/"));
            Files.deleteIfExists(path);

        } catch (IOException e) {
            throw new RuntimeException("Could not delete listing image", e);
        }

        listingImageRepository.delete(image);

    }

    @Override
    @Transactional
    public void deleteById(long id) {
        AppUser currentUser = getCurrentUser();

        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        validateOwner(listing, currentUser);

        listingRepository.delete(listing);
    }

    private AppUser getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private void validateOwner(Listing listing, AppUser currentUser) {
        if (!listing.getAppUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not owner of this listing");
        }
    }

}
