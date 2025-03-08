package itacademy.rentalapp2.controller;

import itacademy.rentalapp2.dto.ApartmentDto;
import itacademy.rentalapp2.dto.ApartmentFilterDto;
import itacademy.rentalapp2.service.ApartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/apartments")
@RequiredArgsConstructor
public class ApartmentController {
    private final ApartmentService apartmentService;

    @GetMapping
    public String getAllApartments(@ModelAttribute("apartmentFilter") ApartmentFilterDto apartmentFilter,
                                  Model model) {
            Page<ApartmentDto> apartmentsPage = apartmentService.getApartmentsByFilter(apartmentFilter);
            model.addAttribute("apartments", apartmentsPage);
            model.addAttribute("apartmentFilter", apartmentFilter);
        return "apartments/list";
    }

    @GetMapping("/save")
    public String showSaveForm(Model model) {
        model.addAttribute("apartment", new ApartmentDto());
        return "apartments/save";
    }

    @PostMapping("/save")
    public String saveApartment(@Valid @ModelAttribute ApartmentDto apartmentDto,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "apartments/save";
        }
        apartmentService.saveApartment(apartmentDto);
        return "redirect:/apartments";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        ApartmentDto apartmentDto = apartmentService.getApartmentById(id);
        model.addAttribute("apartment", apartmentDto);
        return "apartments/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateApartment(@PathVariable Long id,
                                @Valid @ModelAttribute ApartmentDto apartmentDto,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "apartments/edit";
        }
        apartmentService.updateApartment(id, apartmentDto);
        return "redirect:/apartments";
    }

    @GetMapping("/delete/{id}")
    public String deleteApartment(@PathVariable Long id) {
        apartmentService.deleteApartment(id);
        return "redirect:/apartments";
    }
}
