package itacademy.rentalapp2.dto;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "поле \"Номер квартиры\" не может быть пустым")
    private Integer apartmentNumber;

    @NotBlank(message = "поле \"Этаж\" не может быть пустым")
    private Integer floor;

    @NotBlank(message = "поле \"Количество комнат\" не может быть пустым")
    private Integer countOfRooms;

    private Double totalSquare;
    private Long addressId;
    @Nullable
    private AddressDto address = new AddressDto();
}
