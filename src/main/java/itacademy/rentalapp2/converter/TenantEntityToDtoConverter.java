package itacademy.rentalapp2.converter;

import itacademy.rentalapp2.dto.TenantDto;
import itacademy.rentalapp2.entity.TenantEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TenantEntityToDtoConverter implements Converter<TenantEntity, TenantDto> {
    @Override
    public TenantDto convert(TenantEntity entity) {
        return TenantDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .patronymic(entity.getPatronymic())
                .age(entity.getAge())
                .email(entity.getEmail())
                .build();
    }
}
