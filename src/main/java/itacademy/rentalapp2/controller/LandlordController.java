package itacademy.rentalapp2.controller;

import itacademy.rentalapp2.dto.*;
import itacademy.rentalapp2.service.LandlordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/landlords")
@RequiredArgsConstructor
public class LandlordController {
    private final LandlordService landlordService;

    @GetMapping
    public String getAllLandlords(@ModelAttribute("landlordFilter") LandlordFilterDto landlordFilter,
                                  Model model) {
        Page<LandlordDto> landlordPage = landlordService.getLandlordsByFilter(landlordFilter);
        model.addAttribute("landlords", landlordPage);
        model.addAttribute("landlordFilter", landlordFilter);
        return "landlords/list";
    }

    @GetMapping("/save")
    public String showSaveForm(Model model) {
        model.addAttribute("landlord", new LandlordDto());
        return "landlords/save";
    }

    @PostMapping("/save")
    public String saveLandlord(@Valid @ModelAttribute LandlordDto landlordDto,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "landlords/save";
        }
        landlordService.saveLandlord(landlordDto);
        return "redirect:/landlords";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        LandlordDto landlordDto = landlordService.getLandlordById(id);
        model.addAttribute("landlord", landlordDto);
        return "landlords/edit";
    }

    @PostMapping("/edit/{id}")
    public String editLandlord(@PathVariable Long id,
                               @Valid @ModelAttribute LandlordDto landlordDto,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "landlords/edit";
        }
        landlordService.updateLandlord(id, landlordDto);
        return "redirect:/landlords";
    }

    @GetMapping("/delete/{id}")
    public String deleteLandlord(@PathVariable Long id) {
        landlordService.deleteLandlord(id);
        return "redirect:/landlords";
    }

    @GetMapping("/{id}/apartments")
    public String getApartments(@PathVariable Long id,
                                @ModelAttribute("apartmentFilter") ApartmentFilterDto apartmentFilter,
                                Model model) {
        Page<ApartmentDto> apartmentsPage = landlordService.getApartmentsByLandlordId(id, apartmentFilter);
        model.addAttribute("apartments", apartmentsPage);
        model.addAttribute("apartmentFilter", apartmentFilter);
        return "landlords/apartments";
    }
}
