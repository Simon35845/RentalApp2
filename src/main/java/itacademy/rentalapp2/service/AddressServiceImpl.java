package itacademy.rentalapp2.service;

import itacademy.rentalapp2.dto.AddressDto;
import itacademy.rentalapp2.dto.AddressFilterDto;
import itacademy.rentalapp2.entity.AddressEntity;
import itacademy.rentalapp2.exception.DatabaseErrors;
import itacademy.rentalapp2.exception.CustomException;
import itacademy.rentalapp2.exception.ServiceErrors;
import itacademy.rentalapp2.repository.AddressRepository;
import itacademy.rentalapp2.repository.ApartmentRepository;
import itacademy.rentalapp2.specification.AddressSpecification;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressServiceImpl.class);
    private final AddressRepository addressRepository;
    private final ApartmentRepository apartmentRepository;
    private final ConversionService conversionService;

    @Override
    public AddressDto saveAddress(AddressDto addressDto) {
        LOGGER.debug("Saving address: {}", addressDto);
        try {
            AddressEntity addressEntity = conversionService.convert(addressDto, AddressEntity.class);
            AddressEntity savedEntity = addressRepository.save(addressEntity);
            LOGGER.debug("Address saved successfully: {}", savedEntity);
            return conversionService.convert(savedEntity, AddressDto.class);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Error saving address: {}", addressDto, e);
            throw new CustomException(DatabaseErrors.ADDRESS_ALREADY_EXISTS);
        } catch (Exception e) {
            LOGGER.error("Error saving address: {}", addressDto, e);
            throw new CustomException(ServiceErrors.SAVE_ERROR);
        }
    }

    @Override
    public AddressDto updateAddress(Long id, AddressDto addressDto) {
        LOGGER.debug("Updating address with id {}: {}", id, addressDto);
        try {
            AddressEntity addressEntity = setAddressEntity(id, addressDto);
            AddressEntity updatedEntity = addressRepository.save(addressEntity);
            LOGGER.debug("Address updated successfully: {}", updatedEntity);
            return conversionService.convert(updatedEntity, AddressDto.class);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Error updating address with id {}: {}", id, addressDto, e);
            throw new CustomException(DatabaseErrors.ADDRESS_ALREADY_EXISTS);
        } catch (Exception e) {
            LOGGER.error("Error updating address with id {}: {}", id, addressDto, e);
            throw new CustomException(ServiceErrors.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteAddress(Long id) {
        LOGGER.debug("Deleting address with id: {}", id);
        try {
            if (apartmentRepository.existsByAddressId(id)) {
                LOGGER.error("Cannot delete address with id {}: Apartments are linked to this address", id);
                throw new CustomException(DatabaseErrors.ADDRESS_HAS_LINKED_APARTMENTS);
            }
            addressRepository.deleteById(id);
            LOGGER.debug("Address deleted successfully with id: {}", id);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error deleting address with id: {}", id, e);
            throw new CustomException(ServiceErrors.DELETE_ERROR);
        }
    }

    @Override
    public AddressDto getAddressById(Long id) {
        LOGGER.debug("Fetching address by id: {}", id);
        try {
            AddressEntity addressEntity = getAddressEntity(id);
            LOGGER.debug("Address fetched successfully: {}", addressEntity);
            return conversionService.convert(addressEntity, AddressDto.class);
        } catch (Exception e) {
            LOGGER.error("Error fetching address with id: {}", id, e);
            throw new CustomException(ServiceErrors.FIND_BY_ID_ERROR);
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

            Pageable pageable = PageRequest.of(pageNumber, filter.getPageSize(),
                    Sort.by(Sort.Direction.ASC, "id"));

            Specification<AddressEntity> spec = Specification
                    .where(AddressSpecification.cityContains(filter.getCity()))
                    .and(AddressSpecification.streetContains(filter.getStreet()));

            Page<AddressEntity> addressesPage = addressRepository.findAll(spec, pageable);

            if (pageNumber > addressesPage.getTotalPages()) {
                pageNumber = addressesPage.getTotalPages() - 1;
                pageable = PageRequest.of(pageNumber, filter.getPageSize(),
                        Sort.by(Sort.Direction.ASC, "id"));
                addressesPage = addressRepository.findAll(pageable);
            }
            LOGGER.debug("Addresses fetched successfully: {}", addressesPage.getContent());
            return addressesPage.map(addressEntity ->
                    conversionService.convert(addressEntity, AddressDto.class));
        } catch (Exception e) {
            LOGGER.error("Error fetching addresses by filter: {}", filter, e);
            throw new CustomException(ServiceErrors.FIND_BY_FILTER_ERROR);
        }
    }

    private AddressEntity getAddressEntity(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Address not found with id: {}", id);
                    return new CustomException(DatabaseErrors.ADDRESS_NOT_FOUND);
                });
    }

    private AddressEntity setAddressEntity(Long id, AddressDto addressDto) {
        AddressEntity addressEntity = getAddressEntity(id);
        addressEntity.setCity(addressDto.getCity());
        addressEntity.setStreet(addressDto.getStreet());
        addressEntity.setHouseNumber(addressDto.getHouseNumber());
        return addressEntity;
    }
}
