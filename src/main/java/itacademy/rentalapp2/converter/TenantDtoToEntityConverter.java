package itacademy.rentalapp2.converter;

import itacademy.rentalapp2.dto.TenantDto;
import itacademy.rentalapp2.entity.TenantEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TenantDtoToEntityConverter implements Converter<TenantDto, TenantEntity> {
    @Override
    public TenantEntity convert(TenantDto dto) {
        return TenantEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .patronymic(dto.getPatronymic())
                .age(dto.getAge())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .build();
    }
}
