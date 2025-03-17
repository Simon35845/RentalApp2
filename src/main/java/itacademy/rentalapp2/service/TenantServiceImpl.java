package itacademy.rentalapp2.service;

import itacademy.rentalapp2.dto.*;
import itacademy.rentalapp2.entity.ApartmentEntity;
import itacademy.rentalapp2.entity.TenantEntity;
import itacademy.rentalapp2.exception.CustomException;
import itacademy.rentalapp2.exception.DatabaseErrors;
import itacademy.rentalapp2.exception.ServiceErrors;
import itacademy.rentalapp2.repository.ApartmentRepository;
import itacademy.rentalapp2.repository.TenantRepository;
import itacademy.rentalapp2.specification.ApartmentSpecification;
import itacademy.rentalapp2.specification.TenantSpecification;
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
public class TenantServiceImpl implements TenantService {
    public static final Logger LOGGER = LoggerFactory.getLogger(TenantServiceImpl.class);
    private final TenantRepository tenantRepository;
    private final ApartmentRepository apartmentRepository;
    private final ConversionService conversionService;
    private final ApartmentService apartmentService;

    @Override
    public TenantDto saveTenant(TenantDto tenantDto) {
        LOGGER.debug("Saving tenant: {}", tenantDto);
        try {
            TenantEntity tenantEntity = conversionService.convert(tenantDto, TenantEntity.class);
            TenantEntity savedEntity = tenantRepository.save(tenantEntity);
            LOGGER.debug("Tenant saved successfully: {}", savedEntity);
            return conversionService.convert(savedEntity, TenantDto.class);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Error saving tenant: {}", tenantDto, e);
            throw new CustomException(DatabaseErrors.TENANT_ALREADY_EXISTS);
        } catch (Exception e) {
            LOGGER.error("Error saving tenant: {}", tenantDto, e);
            throw new CustomException(ServiceErrors.SAVE_ERROR);
        }
    }

    @Override
    public TenantDto updateTenant(Long id, TenantDto tenantDto) {
        LOGGER.debug("Updating tenant with id {}: {}", id, tenantDto);
        try {
            TenantEntity fetchedEntity = getTenantEntity(id);
            TenantEntity tenantEntity = conversionService.convert(tenantDto, TenantEntity.class);
            tenantEntity.setId(fetchedEntity.getId());
            TenantEntity updatedEntity = tenantRepository.save(tenantEntity);
            LOGGER.debug("Tenant updated successfully: {}", updatedEntity);
            return conversionService.convert(updatedEntity, TenantDto.class);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Error updating tenant with id {}: {}", id, tenantDto, e);
            throw new CustomException(DatabaseErrors.TENANT_ALREADY_EXISTS);
        } catch (Exception e) {
            LOGGER.error("Error updating tenant with id {}: {}", id, tenantDto, e);
            throw new CustomException(ServiceErrors.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteTenant(Long id) {
        LOGGER.debug("Deleting tenant with id {}", id);
        try {
            tenantRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("Error deleting tenant with id: {}", id, e);
            throw new CustomException(ServiceErrors.DELETE_ERROR);
        }
    }

    @Override
    public TenantDto getTenantById(Long id) {
        LOGGER.debug("Fetching tenant by id: {}", id);
        try {
            TenantEntity tenantEntity = getTenantEntity(id);
            LOGGER.debug("Tenant fetched successfully: {}", tenantEntity);
            return conversionService.convert(tenantEntity, TenantDto.class);
        } catch (Exception e) {
            LOGGER.error("Error fetching tenant with id: {}", id, e);
            throw new CustomException(ServiceErrors.FIND_BY_ID_ERROR);
        }
    }

    @Override
    public Page<TenantDto> getTenantsByFilter(TenantFilterDto filter) {
        LOGGER.debug("Fetching tenants by filter: {}", filter);
        try {
            int pageNumber = filter.getPageNumber() - 1;
            if (pageNumber < 0) {
                pageNumber = 0;
            }

            Pageable pageable = PageRequest.of(pageNumber, filter.getPageSize());

            Specification<TenantEntity> spec = Specification
                    .where(TenantSpecification.nameContains(filter.getName()))
                    .and(TenantSpecification.surnameContains(filter.getSurname()))
                    .and(TenantSpecification.patronymicContains(filter.getPatronymic()))
                    .and(TenantSpecification.ageContains(filter.getAge()))
                    .and(TenantSpecification.emailContains(filter.getEmail()));

            Page<TenantEntity> page = tenantRepository.findAll(spec, pageable);

            if (pageNumber > page.getTotalPages()) {
                pageNumber = page.getTotalPages() - 1;
                pageable = PageRequest.of(pageNumber, filter.getPageSize());
                page = tenantRepository.findAll(pageable);
            }
            LOGGER.debug("Tenants fetched successfully: {}", page.getContent());
            return page.map(tenantEntity ->
                    conversionService.convert(tenantEntity, TenantDto.class));
        } catch (Exception e) {
            LOGGER.error("Error fetching tenants by filter: {}", filter, e);
            throw new CustomException(ServiceErrors.FIND_BY_FILTER_ERROR);
        }
    }

    @Override
    public Page<ApartmentDto> getApartmentsByTenantId(Long id, ApartmentFilterDto filter) {
        LOGGER.debug("Fetching apartments by tenant id: {}", id);
        try {
            int pageNumber = filter.getPageNumber() - 1;
            if (pageNumber < 0) {
                pageNumber = 0;
            }

            Pageable pageable = PageRequest.of(pageNumber, filter.getPageSize());

            Specification<ApartmentEntity> spec = Specification
                    .where(ApartmentSpecification.byTenantId(id))
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
            LOGGER.error("Error fetching apartments by tenant id: {}", id, e);
            throw new CustomException(ServiceErrors.FIND_BY_FILTER_ERROR);
        }
    }

    @Override
    public Page<ApartmentDto> getAllApartments(ApartmentFilterDto filter) {
        LOGGER.debug("Fetching all apartments with filter: {}", filter);
        try {
            return apartmentService.getApartmentsByFilter(filter);
        } catch (Exception e) {
            LOGGER.error("Error fetching apartments: {}", filter, e);
            throw new CustomException(ServiceErrors.FIND_BY_FILTER_ERROR);
        }
    }

    @Override
    public void joinApartmentToTenant(Long tenantId, Long apartmentId) {
        LOGGER.debug("Join apartment with id {} to tenant with id: {}", apartmentId, tenantId);
        try {
            TenantEntity tenantEntity = getTenantEntity(tenantId);
            ApartmentEntity apartmentEntity = getApartmentEntity(apartmentId);
            tenantEntity.getApartments().add(apartmentEntity);
            apartmentEntity.getTenants().add(tenantEntity);
//            tenantRepository.save(tenantEntity);
            apartmentRepository.save(apartmentEntity);
            LOGGER.debug("Apartment with id {} joined to tenant with id {} successfully: ",
                    apartmentId, tenantId);
        } catch (Exception e) {
            LOGGER.error("Error joining apartment with id {} to tenant with id: {}", apartmentId, tenantId, e);
            throw new CustomException(ServiceErrors.JOIN_ERROR);
        }
    }

    @Override
    public void detachApartmentFromTenant(Long tenantId, Long apartmentId) {
        LOGGER.debug("Detach apartment with id {} from tenant with id {}", apartmentId, tenantId);
        try {
            TenantEntity tenantEntity = getTenantEntity(tenantId);
            ApartmentEntity apartmentEntity = getApartmentEntity(apartmentId);
            if (apartmentEntity.getTenants().contains(tenantEntity)) {
                tenantEntity.getApartments().remove(apartmentEntity);
                apartmentEntity.getTenants().remove(tenantEntity);
//                tenantRepository.save(tenantEntity);
                apartmentRepository.save(apartmentEntity);
                LOGGER.debug("Apartment with id {} detached from tenant with id {} successfully: ",
                        apartmentId, tenantId);
            } else {
                LOGGER.error("Apartment with id {} is not linked to tenant with id {}", apartmentId, tenantId);
                throw new CustomException(DatabaseErrors.APARTMENT_NOT_LINKED_TO_TENANT);
            }
        } catch (Exception e) {
            LOGGER.error("Error detaching apartment with id {} from tenant with id {}", apartmentId, tenantId, e);
            throw new CustomException(ServiceErrors.DETACH_ERROR);
        }
    }

    private TenantEntity getTenantEntity(Long id) {
        return tenantRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Tenant not found with id: {}", id);
                    return new CustomException(DatabaseErrors.TENANT_NOT_FOUND);
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
