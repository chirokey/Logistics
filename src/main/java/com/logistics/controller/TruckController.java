package com.logistics.controller;

import com.logistics.dto.TruckDTO;
import com.logistics.entity.truck.TruckStatus;
import com.logistics.service.CityService;
import com.logistics.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/trucks")
public class TruckController {
    private TruckService truckService;
    private CityService cityService;

    @Autowired
    public TruckController(TruckService truckService, CityService cityService) {
        this.truckService = truckService;
        this.cityService = cityService;
    }

    @GetMapping()
    public String show(@RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("trucks", truckService.getTrucksOnPageNumber(page));
        model.addAttribute("numberOfPages", truckService.getNumberOfPages());
        model.addAttribute("currentPage", page);
        return "truck/truck";
    }

    @GetMapping("/add")
    public String showAddTruck(Model model) {
        getAddTruckTableModel(model, new TruckDTO());
        return "truck/addTruck";
    }

    @PostMapping("/add")
    public String addTruck(@Valid @ModelAttribute("truck") TruckDTO truck, Errors errors, Model model) {
        if (errors.hasErrors()) {
            getAddTruckTableModel(model, truck);
            return "truck/addTruck";
        }
        truckService.addTruck(truck);
        return "redirect:/trucks";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam Long truckID, Model model) {
        TruckDTO truckDTO = truckService.getTruckByID(truckID);
        getAddTruckTableModel(model, truckDTO);
        return "truck/editTruck";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("truck") TruckDTO truck, Errors errors, Model model) {
        if (errors.hasErrors()) {
            getAddTruckTableModel(model, truck);
            return "truck/editTruck";
        }
        truckService.updateTruck(truck);
        return "redirect:/trucks";
    }

    private void getAddTruckTableModel(Model model, TruckDTO truckDTO) {
        model.addAttribute("truck", truckDTO);
        model.addAttribute("truckStatusList", Arrays.asList(TruckStatus.values()));
        model.addAttribute("citiesList", cityService.getAllCities());
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long truckID) {
        truckService.deleteTruck(truckID);
        return "redirect:/trucks";
    }
}
