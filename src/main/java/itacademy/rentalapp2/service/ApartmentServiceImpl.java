package itacademy.rentalapp2.service;

import itacademy.rentalapp2.dto.AddressDto;
import itacademy.rentalapp2.dto.AddressFilterDto;
import itacademy.rentalapp2.dto.ApartmentDto;
import itacademy.rentalapp2.dto.ApartmentFilterDto;
import itacademy.rentalapp2.entity.AddressEntity;
import itacademy.rentalapp2.entity.ApartmentEntity;
import itacademy.rentalapp2.repository.AddressRepository;
import itacademy.rentalapp2.repository.ApartmentRepository;
import itacademy.rentalapp2.specification.ApartmentSpecification;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
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
            AddressEntity addressEntity = addressRepository.findById(apartmentDto.getAddressId())
                    .orElseThrow(() -> new RuntimeException("Address not found"));
            apartmentEntity.setAddress(addressEntity);
            ApartmentEntity savedEntity = apartmentRepository.save(apartmentEntity);
            LOGGER.debug("Apartment saved successfully: {}", savedEntity);
            return conversionService.convert(savedEntity, ApartmentDto.class);
        } catch (Exception e) {
            LOGGER.error("Error saving apartment: {}", apartmentDto, e);
            throw e;
        }
    }

    @Override
    public ApartmentDto updateApartment(Long id, ApartmentDto apartmentDto) {
        LOGGER.debug("Updating apartment with id {}: {}", id, apartmentDto);
        try {
            ApartmentEntity apartmentEntity = apartmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Apartment not found"));
            apartmentEntity.setApartmentNumber(apartmentDto.getApartmentNumber());
            apartmentEntity.setFloor(apartmentDto.getFloor());
            apartmentEntity.setCountOfRooms(apartmentDto.getCountOfRooms());
            apartmentEntity.setTotalSquare(apartmentDto.getTotalSquare());

            AddressEntity addressEntity = addressRepository.findById(apartmentEntity.getId())
                    .orElseThrow(() -> new RuntimeException("Address not found"));
            apartmentEntity.setAddress(addressEntity);
            ApartmentEntity updatedEntity = apartmentRepository.save(apartmentEntity);
            LOGGER.debug("Apartment updated successfully: {}", updatedEntity);
            return conversionService.convert(updatedEntity, ApartmentDto.class);
        } catch (Exception e) {
            LOGGER.error("Error updating apartment with id {}: {}", id, apartmentDto, e);
            throw e;
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
            throw e;
        }
    }

    @Override
    public ApartmentDto getApartmentById(Long id) {
        LOGGER.debug("Fetching apartment by id: {}", id);
        try {
            ApartmentEntity apartmentEntity = apartmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Apartment not found"));
            LOGGER.debug("Apartment fetched successfully: {}", apartmentEntity);
            return conversionService.convert(apartmentEntity, ApartmentDto.class);
        } catch (Exception e) {
            LOGGER.error("Error fetching apartment with id: {}", id, e);
            throw e;
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

            Page<ApartmentEntity> apartmentsPage = apartmentRepository.findAll(spec, pageable);

            if (pageNumber > apartmentsPage.getTotalPages()) {
                pageNumber = apartmentsPage.getTotalPages() - 1;
                pageable = PageRequest.of(pageNumber, filter.getPageSize());
                apartmentsPage = apartmentRepository.findAll(pageable);
            }
            LOGGER.debug("Apartments fetched successfully: {}", apartmentsPage.getContent());
            return apartmentsPage.map(apartmentEntity ->
                    conversionService.convert(apartmentEntity, ApartmentDto.class));
//            return apartmentsPage.map(apartmentEntity -> {
//                ApartmentDto apartmentDto = conversionService.convert(apartmentEntity, ApartmentDto.class);
//                if (apartmentEntity.getAddress() != null) {
//                    AddressDto addressDto = conversionService.convert(apartmentEntity.getAddress(), AddressDto.class);
//                    apartmentDto.setAddress(addressDto);
//                }
//                return apartmentDto;
//            });
        } catch (Exception e) {
            LOGGER.error("Error fetching apartments by filter: {}", filter, e);
            throw e;
        }
    }

    @Override
    public Page<AddressDto> getAddressesByFilter(AddressFilterDto filter) {
        return addressService.getAddressesByFilter(filter);
    }

    @Override
    public AddressDto getAddressById(Long id) {
        return addressService.getAddressById(id);
    }
}