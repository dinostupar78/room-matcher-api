package hr.tvz.roommatcher.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ListingDTO {
    private String title;

    private String address;

    private String price;

    private Integer size;

    private Integer roomCount;

    private String description;

    private LocalDate availableFrom;

    private Boolean isActive;
}
