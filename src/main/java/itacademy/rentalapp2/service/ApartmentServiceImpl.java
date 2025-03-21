package itacademy.rentalapp2.service;

import itacademy.rentalapp2.dto.AddressDto;
import itacademy.rentalapp2.dto.AddressFilterDto;
import itacademy.rentalapp2.dto.ApartmentDto;
import itacademy.rentalapp2.dto.ApartmentFilterDto;
import itacademy.rentalapp2.entity.AddressEntity;
import itacademy.rentalapp2.entity.ApartmentEntity;
import itacademy.rentalapp2.exception.CustomException;
import itacademy.rentalapp2.exception.DatabaseErrors;
import itacademy.rentalapp2.exception.ServiceErrors;
import itacademy.rentalapp2.repository.AddressRepository;
import itacademy.rentalapp2.repository.ApartmentRepository;
import itacademy.rentalapp2.specification.ApartmentSpecification;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentServiceImpl.class);
    private final ApartmentRepository apartmentRepository;
    private final AddressRepository addressRepository;
    private final ConversionService conversionService;
    private final AddressService addressService;

    @Override
    public ApartmentDto saveApartment(ApartmentDto apartmentDto) {
        LOGGER.debug("Saving apartment {}", apartmentDto);
        try {
            ApartmentEntity apartmentEntity = conversionService.convert(apartmentDto, ApartmentEntity.class);
            AddressEntity addressEntity = getAddressEntity(apartmentDto.getAddressId());
            apartmentEntity.setAddress(addressEntity);
            ApartmentEntity savedEntity = apartmentRepository.save(apartmentEntity);
            LOGGER.debug("Apartment saved successfully: {}", savedEntity);
            return conversionService.convert(savedEntity, ApartmentDto.class);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Error saving apartment: {}", apartmentDto, e);
            throw new CustomException(DatabaseErrors.APARTMENT_ALREADY_EXISTS);
        } catch (Exception e) {
            LOGGER.error("Error saving apartment: {}", apartmentDto, e);
            throw new CustomException(ServiceErrors.SAVE_ERROR);
        }
    }

    @Override
    public ApartmentDto updateApartment(Long id, ApartmentDto apartmentDto) {
        LOGGER.debug("Updating apartment with id {}: {}", id, apartmentDto);
        try {
            ApartmentEntity fetchedEntity = getApartmentEntity(id);
            ApartmentEntity apartmentEntity = conversionService.convert(apartmentDto, ApartmentEntity.class);
            apartmentEntity.setId(fetchedEntity.getId());
            apartmentEntity.setAddress(fetchedEntity.getAddress());
            ApartmentEntity updatedEntity = apartmentRepository.save(apartmentEntity);
            LOGGER.debug("Apartment updated successfully: {}", updatedEntity);
            return conversionService.convert(updatedEntity, ApartmentDto.class);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Error updating apartment with id {}: {}", id, apartmentDto, e);
            throw new CustomException(DatabaseErrors.APARTMENT_ALREADY_EXISTS);
        } catch (Exception e) {
            LOGGER.error("Error updating apartment with id {}: {}", id, apartmentDto, e);
            throw new CustomException(ServiceErrors.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteApartment(Long id) {
        LOGGER.debug("Deleting apartment with id: {}", id);
        try {
            apartmentRepository.deleteById(id);
            LOGGER.debug("Apartment deleted successfully with id: {}", id);
        } catch (Exception e) {
            LOGGER.error("Error deleting apartment with id: {}", id, e);
            throw new CustomException(ServiceErrors.DELETE_ERROR);
        }
    }

    @Override
    public ApartmentDto getApartmentById(Long id) {
        LOGGER.debug("Fetching apartment by id: {}", id);
        try {
            ApartmentEntity apartmentEntity = getApartmentEntity(id);
            LOGGER.debug("Apartment fetched successfully: {}", apartmentEntity);
            return conversionService.convert(apartmentEntity, ApartmentDto.class);
        } catch (Exception e) {
            LOGGER.error("Error fetching apartment with id: {}", id, e);
            throw new CustomException(ServiceErrors.FIND_BY_ID_ERROR);
        }
    }

    @Override
    public Page<ApartmentDto> getApartmentsByFilter(ApartmentFilterDto filter) {
        LOGGER.debug("Fetching apartments by filter: {}", filter);
        try {
            int pageNumber = filter.getPageNumber() - 1;
            if (pageNumber < 0) {
                pageNumber = 0;
            }

            Pageable pageable = PageRequest.of(pageNumber, filter.getPageSize());

            Specification<ApartmentEntity> spec = Specification
                    .where(ApartmentSpecification.apartmentNumberContains(filter.getApartmentNumber())
                            .and(ApartmentSpecification.floorContains(filter.getFloor()))
                            .and(ApartmentSpecification.countOfRoomsContains(filter.getCountOfRooms()))
                            .and(ApartmentSpecification.totalSquareContains(filter.getTotalSquare()))
                            .and(ApartmentSpecification.cityContains(filter.getCity()))
                            .and(ApartmentSpecification.streetContains(filter.getStreet()))
                            .and(ApartmentSpecification.houseNumberContains(filter.getHouseNumber())));

            Page<ApartmentEntity> page = apartmentRepository.findAll(spec, pageable);

            if (pageNumber > page.getTotalPages()) {
                pageNumber = page.getTotalPages() - 1;
                pageable = PageRequest.of(pageNumber, filter.getPageSize());
                page = apartmentRepository.findAll(pageable);
            }
            LOGGER.debug("Apartments fetched successfully: {}", page.getContent());
            return page.map(apartmentEntity ->
                    conversionService.convert(apartmentEntity, ApartmentDto.class));
        } catch (Exception e) {
            LOGGER.error("Error fetching apartments by filter: {}", filter, e);
            throw new CustomException(ServiceErrors.FIND_BY_FILTER_ERROR);
        }
    }

    @Override
    public Page<AddressDto> getAllAddresses(AddressFilterDto filter) {
        try {
            return addressService.getAddressesByFilter(filter);
        } catch (Exception e) {
            LOGGER.error("Error fetching addresses: {}", filter, e);
            throw new CustomException(ServiceErrors.FIND_BY_FILTER_ERROR);
        }
    }

    @Override
    public void joinAnotherAddress(Long apartmentId, Long addressId) {
        LOGGER.debug("Join apartment with id {} to address with id: {}", apartmentId, addressId);
        try {
            ApartmentEntity apartmentEntity = getApartmentEntity(apartmentId);
            AddressEntity addressEntity = getAddressEntity(addressId);
            apartmentEntity.setAddress(addressEntity);
            apartmentRepository.save(apartmentEntity);
            LOGGER.debug("Apartment with id {} joined to address with id {} successfully: ",
                    apartmentId, addressId);
        } catch (Exception e) {
            LOGGER.error("Error joining apartment with id {} to address with id: {}", apartmentId, addressId, e);
            throw new CustomException(ServiceErrors.JOIN_ERROR);
        }
    }

    private AddressEntity getAddressEntity(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Address not found with id: {}", id);
                    return new CustomException(DatabaseErrors.ADDRESS_NOT_FOUND);
                });
    }

    private ApartmentEntity getApartmentEntity(Long id) {
        return apartmentRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Apartment not found with id: {}", id);
                    return new CustomException(DatabaseErrors.APARTMENT_NOT_FOUND);
                });
    }
}