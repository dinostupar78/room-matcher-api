package hr.tvz.roommatcher.dto.listing;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListingRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 50,
            message = "Title must be between 2 and 50 characters")
    private String title;

    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 150, message = "Address must be between 5 and 150 characters")
    private String address;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Price must be a valid number with up to 8 digits and 2 decimal places")
    private BigDecimal price;

    @NotNull(message = "Size is required")
    @Min(value = 5, message = "Size must be at least 5 m²")
    @Max(value = 200, message = "Size must not be greater than 200 m²")
    private Integer size;

    @NotNull(message = "Room count is required")
    @Min(value = 1, message = "Room count must be at least 1")
    @Max(value = 20, message = "Room count must not be greater than 20")
    private Integer roomCount;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Available from date is required")
    @FutureOrPresent(message = "Available from date must be today or in the future")
    private LocalDate availableFrom;

    @NotNull(message = "Active status is required")
    private Boolean isActive;
}
