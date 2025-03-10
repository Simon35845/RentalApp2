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
public class AddressDto {
    private Long id;

    @NotBlank(message = "field \"City\" can't be empty")
    private String city;

    @NotBlank(message = "field \"Street\" can't be empty")
    private String street;

    @NotNull(message = "field \"House Number\" can't be empty")
    private Integer houseNumber;
}
