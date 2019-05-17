package com.logistics.controller;

import com.logistics.service.DriverService;
import com.logistics.service.TruckService;
import com.logistics.dto.user.DriverDTO;
import com.logistics.entity.user.driver.DriverStatus;
import com.logistics.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/drivers")
class DriverController {
    private DriverService driverService;
    private TruckService truckService;
    private CityService cityService;

    @Autowired
    public DriverController(DriverService driverService, CityService cityService, TruckService truckService) {
        this.driverService = driverService;
        this.cityService = cityService;
        this.truckService = truckService;
    }

    @GetMapping()
    public String show(@RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("drivers", driverService.getDriversOnPageNumber(page));
        model.addAttribute("numberOfPages", driverService.getNumberOfPages());
        model.addAttribute("currentPage", page);
        return "driver/driver";
    }

    @GetMapping("/add")
    public String showAddDriver(Model model) {
        getAddDriverTableModel(model, new DriverDTO());
        return "driver/addDriver";
    }

    @PostMapping("/add")
    public String addDriver(@Valid @ModelAttribute("driver") DriverDTO driver, Errors errors, Model model) {
        if (errors.hasErrors()) {
            getAddDriverTableModel(model, driver);
            return "driver/addDriver";
        }
        driverService.addDriver(driver);
        return "redirect:/drivers";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam Long driverID, Model model) {
        DriverDTO driverDTO = driverService.getDriverByID(driverID);
        getAddDriverTableModel(model, driverDTO);
        return "driver/editDriver";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("driver") DriverDTO driver, Errors errors, Model model) {
        if (errors.hasErrors() && !(errors.hasFieldErrors("password") || errors.hasFieldErrors("matchingPassword"))) {
            getAddDriverTableModel(model, driver);
            return "driver/editDriver";
        }
        driver.setPassword("");
        driver.setMatchingPassword("");
        driverService.updateDriver(driver);
        return "redirect:/drivers";
    }

    private void getAddDriverTableModel(Model model, DriverDTO driverDTO) {
        model.addAttribute("driver", driverDTO);
        model.addAttribute("driverStatusList", Arrays.asList(DriverStatus.values()));
        model.addAttribute("citiesList", cityService.getAllCities());
        model.addAttribute("trucksList", truckService.getAllTrucks());
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long driverID) {
        driverService.deleteDriver(driverID);
        return "redirect:/drivers";
    }
}
