package itacademy.rentalapp2.controller;

import itacademy.rentalapp2.dto.*;
import itacademy.rentalapp2.service.LandlordService;
import itacademy.rentalapp2.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/tenants")
@RequiredArgsConstructor
public class TenantController {
    public static final Logger LOGGER = LoggerFactory.getLogger(TenantController.class);
    private final TenantService tenantService;

    @GetMapping
    public String getAllTenants(@ModelAttribute("tenantFilter") TenantFilterDto tenantFilter,
                                  Model model) {
        Page<TenantDto> tenantPage = tenantService.getTenantsByFilter(tenantFilter);
        model.addAttribute("tenants", tenantPage);
        model.addAttribute("tenantFilter", tenantFilter);
        return "tenants/list";
    }

    @GetMapping("/save")
    public String showSaveForm(Model model) {
        model.addAttribute("tenant", new TenantDto());
        return "tenants/save";
    }

    @PostMapping("/save")
    public String saveTenant(@Valid @ModelAttribute TenantDto tenantDto,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "tenants/save";
        }
        tenantService.saveTenant(tenantDto);
        return "redirect:/tenants";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        TenantDto tenantDto = tenantService.getTenantById(id);
        model.addAttribute("tenant", tenantDto);
        return "tenants/edit";
    }

    @PostMapping("/edit/{id}")
    public String editTenant(@PathVariable Long id,
                               @Valid @ModelAttribute TenantDto tenantDto,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "tenants/edit";
        }
        tenantService.updateTenant(id, tenantDto);
        return "redirect:/tenants";
    }

    @GetMapping("/delete/{id}")
    public String deleteTenant(@PathVariable Long id) {
        tenantService.deleteTenant(id);
        return "redirect:/tenants";
    }

//    @GetMapping("/{id}/apartments")
//    public String getApartments(@PathVariable Long id,
//                                @ModelAttribute("apartmentFilter") ApartmentFilterDto apartmentFilter,
//                                Model model) {
//        LandlordDto landlordDto = landlordService.getLandlordById(id);
//        model.addAttribute("landlord", landlordDto);
//        Page<ApartmentDto> apartmentsPage = landlordService.getApartmentsByLandlordId(id, apartmentFilter);
//        model.addAttribute("apartments", apartmentsPage);
//        model.addAttribute("apartmentFilter", apartmentFilter);
//        return "landlords/apartments";
//    }
//
//    @GetMapping("/{id}/add-apartments")
//    public String showAddApartmentsPage(@PathVariable Long id,
//                                        @ModelAttribute("apartmentFilter") ApartmentFilterDto apartmentFilter,
//                                        Model model) {
//        Page<ApartmentDto> apartmentsPage = landlordService.getAllApartments(apartmentFilter);
//        model.addAttribute("apartments", apartmentsPage);
//        model.addAttribute("id", id);
//        return "landlords/add-apartments";
//    }
//
//    @PostMapping("/{id}/add-apartments")
//    public String joinApartmentToLandlord(@PathVariable Long id,
//                                          @RequestParam Long apartmentId) {
//        landlordService.joinApartmentToLandlord(id, apartmentId);
//        return "redirect:/landlords/" + id + "/apartments";
//    }
//
//    @PostMapping("/{id}/apartments/detach")
//    public String detachApartmentFromLandlord(@PathVariable Long id,
//                                              @RequestParam Long apartmentId) {
//        landlordService.detachApartmentFromLandlord(id, apartmentId);
//        return "redirect:/landlords/" + id + "/apartments";
//    }
}
