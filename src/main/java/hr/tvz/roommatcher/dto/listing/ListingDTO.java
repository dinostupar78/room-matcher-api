package hr.tvz.roommatcher.dto.listing;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ListingDTO {
    private String title;

    private String address;

    private BigDecimal price;

    private Integer size;

    private Integer roomCount;

    private String description;

    private LocalDate availableFrom;

    private Boolean isActive;
}
