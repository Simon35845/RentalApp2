package itacademy.rentalapp2.dto;


import jakarta.annotation.Nullable;
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

    @NotNull(message = "поле \"Номер квартиры\" не может быть пустым")
    private Integer apartmentNumber;

    @NotNull(message = "поле \"Этаж\" не может быть пустым")
    private Integer floor;

    @NotNull(message = "поле \"Количество комнат\" не может быть пустым")
    private Integer countOfRooms;

    private Double totalSquare;

    @Nullable
    private Long addressId;
    @Nullable
    private AddressDto address = new AddressDto();
}
