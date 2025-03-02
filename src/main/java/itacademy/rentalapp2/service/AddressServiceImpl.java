package itacademy.rentalapp2.service;

import itacademy.rentalapp2.dto.AddressDto;
import itacademy.rentalapp2.dto.AddressFilterDto;
import itacademy.rentalapp2.entity.AddressEntity;
import itacademy.rentalapp2.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressServiceImpl.class);
    private final AddressRepository addressRepository;
    private final ConversionService conversionService;

    @Override
    public AddressDto saveAddress(AddressDto addressDto) {
        LOGGER.debug("Saving address: {}", addressDto);
        try {
            AddressEntity addressEntity = conversionService.convert(addressDto, AddressEntity.class);
            AddressEntity savedEntity = addressRepository.save(addressEntity);
            LOGGER.debug("Address saved successfully: {}", savedEntity);
            return conversionService.convert(savedEntity, AddressDto.class);
        } catch (Exception e) {
            LOGGER.error("Error saving address: {}", addressDto, e);
            throw e;
        }
    }

    @Override
    public AddressDto updateAddress(Long id, AddressDto addressDto) {
        LOGGER.debug("Updating address with id {}: {}", id, addressDto);
        try {
            AddressEntity addressEntity = addressRepository.findById(id)
                    .orElseThrow(() -> {
                        LOGGER.error("Address not found with id: {}", id);
                        return new RuntimeException("Address not found");
                    });
            addressEntity.setCity(addressDto.getCity());
            addressEntity.setStreet(addressDto.getStreet());
            addressEntity.setHouseNumber(addressDto.getHouseNumber());
            AddressEntity updatedEntity = addressRepository.save(addressEntity);
            LOGGER.error("Address updated successfully: {}", updatedEntity);
            return conversionService.convert(updatedEntity, AddressDto.class);
        } catch (Exception e) {
            LOGGER.error("Error updating address with id {}: {}", id, addressDto, e);
            throw e;
        }
    }

    @Override
    public void deleteAddress(Long id) {
        LOGGER.debug("Deleting address with id: {}", id);
        try {
            addressRepository.deleteById(id);
            LOGGER.debug("Address deleted successfully with id: {}", id);
        } catch (Exception e) {
            LOGGER.error("Error deleting address with id: {}", id, e);
            throw e;
        }
    }

    @Override
    public AddressDto getAddressById(Long id) {
        LOGGER.debug("Fetching address by id: {}", id);
        try {
            AddressEntity addressEntity = addressRepository.findById(id)
                    .orElseThrow(() -> {
                        LOGGER.error("Address not found with id: {}", id);
                        return new RuntimeException("Address not found");
                    });
            LOGGER.debug("Address fetched successfully: {}", addressEntity);
            return conversionService.convert(addressEntity, AddressDto.class);
        } catch (Exception e) {
            LOGGER.error("Error fetching address with id: {}", id, e);
            throw e;
        }
    }

    @Override
    public Page<AddressDto> getAddressesByFilter(AddressFilterDto filter) {
        LOGGER.debug("Fetching addresses by filter: {}", filter);
        try {
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
            LOGGER.debug("Addresses fetched successfully: {}", addressesPage.getContent());
            return addressesPage.map(addressEntity ->
                    conversionService.convert(addressEntity, AddressDto.class));
        } catch (Exception e) {
            LOGGER.error("Error fetching addresses by filter: {}", filter, e);
            throw e;
        }
    }
}
