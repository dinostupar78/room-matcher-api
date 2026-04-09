package hr.tvz.roommatcher.mapper;
import hr.tvz.roommatcher.dto.ListingRequestDTO;
import hr.tvz.roommatcher.dto.ListingResponseDTO;
import hr.tvz.roommatcher.model.Listing;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ListingMapper {
    ListingResponseDTO toListingResponseDTO(Listing listing);

    Listing toListing(ListingRequestDTO listingRequestDTO);


}
