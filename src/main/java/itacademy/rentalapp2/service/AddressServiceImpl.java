package itacademy.rentalapp2.service;

import itacademy.rentalapp2.converter.AddressDtoToEntityConverter;
import itacademy.rentalapp2.converter.AddressEntityToDtoConverter;
import itacademy.rentalapp2.dto.AddressDto;
import itacademy.rentalapp2.entity.AddressEntity;
import itacademy.rentalapp2.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressDtoToEntityConverter dtoToEntityConverter;
    private final AddressEntityToDtoConverter entityToDtoConverter;

    @Override
    public AddressDto saveAddress(AddressDto addressDto) {
        AddressEntity addressEntity = dtoToEntityConverter.convert(addressDto);
        AddressEntity savedEntity = addressRepository.save(addressEntity);
        return entityToDtoConverter.convert(savedEntity);
    }

    @Override
    public AddressDto updateAddress(Long id, AddressDto addressDto) {
        AddressEntity addressEntity = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        addressEntity.setCity(addressDto.getCity());
        addressEntity.setStreet(addressDto.getStreet());
        addressEntity.setHouseNumber(addressDto.getHouseNumber());
        AddressEntity updatedEntity = addressRepository.save(addressEntity);
        return entityToDtoConverter.convert(updatedEntity);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public AddressDto getAddressById(Long id) {
        AddressEntity addressEntity = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        return entityToDtoConverter.convert(addressEntity);
    }

    @Override
    public Page<AddressDto> getAllAddresses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AddressEntity> addressEntities = addressRepository.findAll(pageable);
        return addressEntities.map(entityToDtoConverter::convert);
    }

    @Override
    public Page<AddressDto> getAddressesByCity(String filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AddressEntity> addressEntities = addressRepository.findByCity(filter, pageable);
        return addressEntities.map(entityToDtoConverter::convert);
    }
}
