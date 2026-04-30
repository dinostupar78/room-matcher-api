package hr.tvz.roommatematcher.mapper;
import hr.tvz.roommatematcher.dto.listing.ListingRequest;
import hr.tvz.roommatematcher.dto.listing.ListingResponse;
import hr.tvz.roommatematcher.dto.listing.UpdateListingRequest;
import hr.tvz.roommatematcher.model.Listing;
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
