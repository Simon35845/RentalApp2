package itacademy.rentalapp2.service;

import itacademy.rentalapp2.dto.AddressDto;
import itacademy.rentalapp2.dto.AddressFilterDto;
import itacademy.rentalapp2.entity.AddressEntity;
import itacademy.rentalapp2.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ConversionService conversionService;

    @Override
    public AddressDto saveAddress(AddressDto addressDto) {
        AddressEntity addressEntity = conversionService.convert(addressDto, AddressEntity.class);
        AddressEntity savedEntity = addressRepository.save(addressEntity);
        return conversionService.convert(savedEntity, AddressDto.class);
    }

    @Override
    public AddressDto updateAddress(Long id, AddressDto addressDto) {
        AddressEntity addressEntity = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        addressEntity.setCity(addressDto.getCity());
        addressEntity.setStreet(addressDto.getStreet());
        addressEntity.setHouseNumber(addressDto.getHouseNumber());
        AddressEntity updatedEntity = addressRepository.save(addressEntity);
        return conversionService.convert(updatedEntity, AddressDto.class);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public AddressDto getAddressById(Long id) {
        AddressEntity addressEntity = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        return conversionService.convert(addressEntity, AddressDto.class);
    }

    @Override
    public Page<AddressDto> getAddressesByFilter(AddressFilterDto filter) {
        int pageNumber = filter.getPageNumber() - 1;

        if (pageNumber < 0) {
            pageNumber = 0;
        }

        Pageable pageable = PageRequest.of(pageNumber, filter.getPageSize());
        Page<AddressEntity> addressesPage;

        String city = filter.getCity();
        String street = filter.getStreet();

        if (StringUtils.hasText(city) && StringUtils.hasText(street)) {
            addressesPage = addressRepository.findByCityIgnoreCaseAndStreetIgnoreCase(
                    filter.getCity(), filter.getStreet(), pageable);
        } else if (StringUtils.hasText(city)) {
            addressesPage = addressRepository.findByCityIgnoreCase(
                    filter.getCity(), pageable);
        } else if (StringUtils.hasText(street)) {
            addressesPage = addressRepository.findByStreetIgnoreCase(
                    filter.getStreet(), pageable);
        } else {
            addressesPage = addressRepository.findAll(pageable);
        }

        if (pageNumber >= addressesPage.getTotalPages()) {
            pageNumber = addressesPage.getTotalPages() - 1;
            pageable = PageRequest.of(pageNumber, filter.getPageSize());
            addressesPage = addressRepository.findAll(pageable);
        }

        return addressesPage.map(addressEntity ->
                conversionService.convert(addressEntity, AddressDto.class));
    }
}
