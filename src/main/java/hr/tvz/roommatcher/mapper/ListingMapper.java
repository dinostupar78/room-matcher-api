package hr.tvz.roommatcher.mapper;
import hr.tvz.roommatcher.dto.listing.ListingRequest;
import hr.tvz.roommatcher.dto.listing.ListingResponse;
import hr.tvz.roommatcher.dto.listing.UpdateListingRequest;
import hr.tvz.roommatcher.model.Listing;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ListingMapper {
    ListingResponse toListingResponse(Listing listing);

    Listing toListing(ListingRequest listingRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appUser", ignore = true)
    void updateListingFromRequest(UpdateListingRequest request, @MappingTarget Listing listing);


}
