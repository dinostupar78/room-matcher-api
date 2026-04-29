package hr.tvz.roommatcher.dto.listing;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateListingRequest (
        @Size(min = 2, max = 150, message = "Title must be between 2 and 150 characters")
        String title,

        @Size(min = 5, max = 150, message = "Address must be between 5 and 150 characters")
        String address,

        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        @Digits(integer = 8, fraction = 2, message = "Price must be a valid monetary amount with up to 8 digits and 2 decimal places")
        BigDecimal price,

        @Min(value = 5, message = "Size must be at least 5 square meters")
        @Max(value = 200, message = "Size must be at most 200 square meters")
        Integer size,

        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description,

        @FutureOrPresent(message = "Available from date must be today or in the future")
        LocalDate availableFrom,

        Boolean isActive
) {}


