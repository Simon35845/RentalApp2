package itacademy.rentalapp2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentDto {
    private Long id;

    @NotBlank(message = "field \"Apartment Number\" can't be empty")
    private Integer apartmentNumber;

    private Integer floor;
    private Integer countOfRooms;
    private Double totalSquare;

    @NotNull(message = "field \"Address\" can't be empty")
    private Long addressId;
}
