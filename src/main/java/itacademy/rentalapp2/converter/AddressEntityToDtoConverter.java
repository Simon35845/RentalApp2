package itacademy.rentalapp2.converter;

import itacademy.rentalapp2.dto.AddressDto;
import itacademy.rentalapp2.entity.AddressEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddressEntityToDtoConverter implements Converter<AddressEntity, AddressDto> {
    @Override
    public AddressDto convert(AddressEntity entity) {
        return AddressDto.builder()
                .id(entity.getId())
                .city(entity.getCity())
                .street(entity.getStreet())
                .houseNumber(entity.getHouseNumber())
                .build();
    }
}
