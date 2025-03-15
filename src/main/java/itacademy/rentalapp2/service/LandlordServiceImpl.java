package itacademy.rentalapp2.service;

import itacademy.rentalapp2.dto.ApartmentDto;
import itacademy.rentalapp2.dto.ApartmentFilterDto;
import itacademy.rentalapp2.dto.LandlordDto;
import itacademy.rentalapp2.dto.LandlordFilterDto;
import itacademy.rentalapp2.entity.ApartmentEntity;
import itacademy.rentalapp2.entity.LandlordEntity;
import itacademy.rentalapp2.exception.CustomException;
import itacademy.rentalapp2.exception.DatabaseErrors;
import itacademy.rentalapp2.exception.ServiceErrors;
import itacademy.rentalapp2.repository.ApartmentRepository;
import itacademy.rentalapp2.repository.LandlordRepository;
import itacademy.rentalapp2.specification.ApartmentSpecification;
import itacademy.rentalapp2.specification.LandlordSpecification;
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

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LandlordServiceImpl implements LandlordService {
    public static final Logger LOGGER = LoggerFactory.getLogger(LandlordServiceImpl.class);
    private final LandlordRepository landlordRepository;
    private final ApartmentRepository apartmentRepository;
    private final ConversionService conversionService;
    private final ApartmentService apartmentService;

    @Override
    public LandlordDto saveLandlord(LandlordDto landlordDto) {
        LOGGER.debug("Saving landlord: {}", landlordDto);
        try {
            LandlordEntity landlordEntity = conversionService.convert(landlordDto, LandlordEntity.class);
            LandlordEntity savedEntity = landlordRepository.save(landlordEntity);
            LOGGER.debug("Landlord saved successfully: {}", savedEntity);
            return conversionService.convert(savedEntity, LandlordDto.class);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Error saving landlord: {}", landlordDto, e);
            throw new CustomException(DatabaseErrors.LANDLORD_ALREADY_EXISTS);
        } catch (Exception e) {
            LOGGER.error("Error saving landlord: {}", landlordDto, e);
            throw new CustomException(ServiceErrors.SAVE_ERROR);
        }
    }

    public LandlordDto updateLandlord(Long id, LandlordDto landlordDto) {
        LOGGER.debug("Updating landlord with id {}: {}", id, landlordDto);
        try {
            LandlordEntity fetchedEntity = getLandlordEntity(id);
            LandlordEntity landlordEntity = conversionService.convert(landlordDto, LandlordEntity.class);
            landlordEntity.setId(fetchedEntity.getId());
            LandlordEntity updatedEntity = landlordRepository.save(landlordEntity);
            LOGGER.debug("Landlord updated successfully: {}", updatedEntity);
            return conversionService.convert(updatedEntity, LandlordDto.class);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Error updating landlord with id {}: {}", id, landlordDto, e);
            throw new CustomException(DatabaseErrors.LANDLORD_ALREADY_EXISTS);
        } catch (Exception e) {
            LOGGER.error("Error updating landlord with id {}: {}", id, landlordDto, e);
            throw new CustomException(ServiceErrors.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteLandlord(Long id) {
        LOGGER.debug("Deleting landlord with id {}", id);
        try {
            landlordRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("Error deleting landlord with id: {}", id, e);
            throw new CustomException(ServiceErrors.DELETE_ERROR);
        }
    }

    @Override
    public LandlordDto getLandlordById(Long id) {
        LOGGER.debug("Fetching landlord by id: {}", id);
        try {
            LandlordEntity landlordEntity = getLandlordEntity(id);
            LOGGER.debug("Landlord fetched successfully: {}", landlordEntity);
            return conversionService.convert(landlordEntity, LandlordDto.class);
        } catch (Exception e) {
            LOGGER.error("Error fetching landlord with id: {}", id, e);
            throw new CustomException(ServiceErrors.FIND_BY_ID_ERROR);
        }
    }

    @Override
    public Page<LandlordDto> getLandlordsByFilter(LandlordFilterDto filter) {
        LOGGER.debug("Fetching landlords by filter: {}", filter);
        try {
            int pageNumber = filter.getPageNumber() - 1;
            if (pageNumber < 0) {
                pageNumber = 0;
            }

            Pageable pageable = PageRequest.of(pageNumber, filter.getPageSize());

            Specification<LandlordEntity> spec = Specification
                    .where(LandlordSpecification.nameContains(filter.getName()))
                    .and(LandlordSpecification.surnameContains(filter.getSurname()))
                    .and(LandlordSpecification.patronymicContains(filter.getPatronymic()))
                    .and(LandlordSpecification.ageContains(filter.getAge()))
                    .and(LandlordSpecification.emailContains(filter.getEmail()));

            Page<LandlordEntity> page = landlordRepository.findAll(spec, pageable);

            if (pageNumber > page.getTotalPages()) {
                pageNumber = page.getTotalPages() - 1;
                pageable = PageRequest.of(pageNumber, filter.getPageSize());
                page = landlordRepository.findAll(pageable);
            }
            LOGGER.debug("Landlords fetched successfully: {}", page.getContent());
            return page.map(landlordEntity ->
                    conversionService.convert(landlordEntity, LandlordDto.class));
        } catch (Exception e) {
            LOGGER.error("Error fetching landlords by filter: {}", filter, e);
            throw new CustomException(ServiceErrors.FIND_BY_FILTER_ERROR);
        }
    }

    @Override
    public Page<ApartmentDto> getApartmentsByLandlordId(Long id, ApartmentFilterDto filter) {
        LOGGER.debug("Fetching apartments by landlord id: {}", id);
        try {
            int pageNumber = filter.getPageNumber() - 1;
            if (pageNumber < 0) {
                pageNumber = 0;
            }

            Pageable pageable = PageRequest.of(pageNumber, filter.getPageSize());

            Specification<ApartmentEntity> spec = Specification
                    .where(ApartmentSpecification.byLandlordId(id))
                    .and(ApartmentSpecification.apartmentNumberContains(filter.getApartmentNumber()))
                    .and(ApartmentSpecification.floorContains(filter.getFloor()))
                    .and(ApartmentSpecification.countOfRoomsContains(filter.getCountOfRooms()))
                    .and(ApartmentSpecification.totalSquareContains(filter.getTotalSquare()))
                    .and(ApartmentSpecification.cityContains(filter.getCity()))
                    .and(ApartmentSpecification.streetContains(filter.getStreet()))
                    .and(ApartmentSpecification.houseNumberContains(filter.getHouseNumber()));

            Page<ApartmentEntity> page = apartmentRepository.findAll(spec, pageable);

            if (pageNumber > page.getTotalPages()) {
                pageNumber = page.getTotalPages() - 1;
                pageable = PageRequest.of(pageNumber, filter.getPageSize());
                page = apartmentRepository.findAll(spec, pageable);
            }
            LOGGER.debug("Apartments fetched successfully: {}", page.getContent());
            return page.map(apartmentEntity ->
                    conversionService.convert(apartmentEntity, ApartmentDto.class));
        } catch (Exception e) {
            LOGGER.error("Error fetching apartments by landlord id: {}", id, e);
            throw new CustomException(ServiceErrors.FIND_BY_FILTER_ERROR);
        }
    }

    @Override
    public Page<ApartmentDto> getAllApartments(ApartmentFilterDto filter, Long landlordId) {
        LOGGER.debug("Fetching all apartments with filter: {}", filter);
        try {
            return apartmentService.getApartmentsByFilter(filter);
        } catch (Exception e) {
            LOGGER.error("Error fetching apartments: {}", filter, e);
            throw new CustomException(ServiceErrors.FIND_BY_FILTER_ERROR);
        }
    }

    @Override
    public void addApartmentsToLandlord(Long landlordId, List<Long> apartmentIds) {
        LOGGER.debug("Adding apartments to landlord with id: {}", landlordId);
        try {
            LandlordEntity landlordEntity = getLandlordEntity(landlordId);
            List<ApartmentEntity> relatedApartments = apartmentRepository.findByLandlordId(landlordId);

            for (ApartmentEntity apartment : relatedApartments) {
                if (!apartmentIds.contains(apartment.getId())) {
                    apartment.setLandlord(null);
                }
            }

            List<ApartmentEntity> newApartments = apartmentRepository.findAllById(apartmentIds);
            for (ApartmentEntity apartment : newApartments) {
                apartment.setLandlord(landlordEntity);
            }

            apartmentRepository.saveAll(relatedApartments);
            apartmentRepository.saveAll(newApartments);
            LOGGER.debug("Apartments updated successfully for landlord: {}", landlordId);
        } catch (Exception e) {
            LOGGER.error("Error updating apartments for landlord: {}", landlordId, e);
            throw new CustomException(ServiceErrors.UPDATE_ERROR);
        }
    }

    private LandlordEntity getLandlordEntity(Long id) {
        return landlordRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Landlord not found with id: {}", id);
                    return new CustomException(DatabaseErrors.LANDLORD_NOT_FOUND);
                });
    }
}
