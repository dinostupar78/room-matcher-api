package hr.tvz.roommatcher.dto.listing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ListingResponse(
        Long id,
        String title,
        String address,
        BigDecimal price,
        Integer size,
        Integer roomCount,
        String description,
        LocalDate availableFrom,
        Boolean isActive,
        List<ListingImageResponse> images
) {}
