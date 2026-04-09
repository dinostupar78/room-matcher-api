package hr.tvz.roommatcher.dto;

import java.time.LocalDate;

public record ListingResponseDTO(
        Long id,
        String title,
        String address,
        String price,
        Integer size,
        Integer roomCount,
        String description,
        LocalDate availableFrom,
        Boolean isActive
) {}
