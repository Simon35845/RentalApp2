package itacademy.rentalapp2.converter;

import itacademy.rentalapp2.dto.ApartmentDto;
import itacademy.rentalapp2.entity.ApartmentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApartmentEntityToDtoConverter implements Converter<ApartmentEntity, ApartmentDto> {
    private final AddressEntityToDtoConverter addressConverter;

    @Override
    public ApartmentDto convert(ApartmentEntity entity) {
        return ApartmentDto.builder()
                .id(entity.getId())
                .apartmentNumber(entity.getApartmentNumber())
                .floor(entity.getFloor())
                .countOfRooms(entity.getCountOfRooms())
                .totalSquare(entity.getTotalSquare())
                .addressId(entity.getAddress().getId())
                .address(addressConverter.convert(entity.getAddress()))
                .build();
    }
}
