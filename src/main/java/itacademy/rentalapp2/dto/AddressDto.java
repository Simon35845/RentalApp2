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

    @NotBlank(message = "поле \"Город\" не может быть пустым")
    private String city;

    @NotBlank(message = "поле \"Улица\" не может быть пустым")
    private String street;

    @NotNull(message = "поле \"Номер дома\" не может быть пустым")
    private Integer houseNumber;
}
