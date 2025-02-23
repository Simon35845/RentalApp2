package itacademy.rentalapp2.converter;

import itacademy.rentalapp2.dto.AddressDto;
import itacademy.rentalapp2.entity.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddressDtoToEntityConverter implements Converter<AddressDto, AddressEntity> {
    @Override
    public AddressEntity convert(AddressDto dto) {
        return AddressEntity.builder()
                .id(dto.getId())
                .city(dto.getCity())
                .street(dto.getStreet())
                .houseNumber(dto.getHouseNumber())
                .build();
    }
}
