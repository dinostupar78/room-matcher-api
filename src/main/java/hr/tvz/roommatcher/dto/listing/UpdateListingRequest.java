package hr.tvz.roommatcher.dto.listing;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateListingRequest {
    private String title;
    private String address;
    private BigDecimal price;
    private Integer size;
    private Integer roomCount;
    private String description;
    private LocalDate availableFrom;
    private Boolean isActive;
}
