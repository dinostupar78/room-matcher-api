package hr.tvz.roommatcher.service;

import hr.tvz.roommatcher.dto.listing.ListingRequest;
import hr.tvz.roommatcher.dto.listing.ListingResponse;
import hr.tvz.roommatcher.dto.listing.UpdateListingRequest;
import hr.tvz.roommatcher.mapper.ListingMapper;
import hr.tvz.roommatcher.model.AppUser;
import hr.tvz.roommatcher.model.Listing;
import hr.tvz.roommatcher.model.ListingImage;
import hr.tvz.roommatcher.repository.AppUserJpaRepository;
import hr.tvz.roommatcher.repository.ListingImageJpaRepository;
import hr.tvz.roommatcher.repository.ListingJpaRepository;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class ListingServiceImpl implements ListingService {

    private final ListingJpaRepository listingRepository;
    private final AppUserJpaRepository appUserRepository;
    private final ListingImageJpaRepository listingImageRepository;
    private final ListingMapper listingMapper;


    @Override
    public List<ListingResponse> findAll() {
        return listingRepository.findAll().stream()
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
    public ListingResponse updateListing(Long id, UpdateListingRequest updateListingRequest) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Listing with id " + id + " not found."));

        listingMapper.updateListingFromRequest(updateListingRequest, listing);

        Listing updatedListing = listingRepository.save(listing);
        return listingMapper.toListingResponse(updatedListing);

    }

    @Override
    public ListingResponse uploadImage(Long listingId, MultipartFile file) {
        AppUser currentUser = getCurrentUser();

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        if (!listing.getAppUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not owner of this listing");
        }

        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        try{
            String uploadDir = "uploads/listings/";
            String fileName = listingId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            ListingImage image = ListingImage.builder()
                    .imageUrl("/uploads/listings/" + fileName)
                    .listing(listing)
                    .build();

            listingImageRepository.save(image);

            Listing updatedListing = listingRepository.findById(listingId)
                    .orElseThrow(() -> new RuntimeException("Listing not found"));

            return listingMapper.toListingResponse(updatedListing);

        } catch (IOException e) {
            throw new RuntimeException("Could not upload listing image");
        }

    }

    @Override
    public void deleteImage(Long listingId, Long imageId) {
        AppUser currentUser = getCurrentUser();

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        if (!listing.getAppUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not owner of this listing");
        }

        ListingImage image = listingImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        if (!image.getListing().getId().equals(listingId)) {
            throw new RuntimeException("Image does not belong to this listing");
        }

        try {
            Path path = Paths.get(image.getImageUrl().replaceFirst("^/uploads/", "uploads/"));
            Files.deleteIfExists(path);

        } catch (IOException ignored) {
        }

        listingImageRepository.delete(image);


    }

    @Override
    public void deleteById(long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        listingRepository.delete(listing);
    }

    private AppUser getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
