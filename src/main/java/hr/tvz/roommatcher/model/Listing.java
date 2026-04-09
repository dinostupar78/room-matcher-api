package hr.tvz.roommatcher.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Listing {
    private Long id;

    private String title;

    private String address;

    private String price;

    private Integer size;

    private Integer roomCount;

    private String description;

    private LocalDate availableFrom;

    private Boolean isActive;
}
