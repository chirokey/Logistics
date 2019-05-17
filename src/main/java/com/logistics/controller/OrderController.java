package com.logistics.controller;

import com.logistics.dto.CargoDTO;
import com.logistics.dto.LocationDTO;
import com.logistics.service.*;
import com.logistics.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for showing orders and adding new orders
 * Adding orders has 3 steps:
 * 1st step: adding loads
 * 2nd step: adding truck
 * 3rd step: adding driver
 */
@Controller
@RequestMapping("/orders")
@SessionAttributes({"order", "cargoList", "citiesList", "step1", "step2", "step3", "routeDistance"})
public class OrderController {
    private CityService cityService;
    private OrderService orderService;
    private WaypointService waypointService;
    private TruckService truckService;
    private DriverService driverService;
    private CargoService cargoService;

    @Autowired
    public OrderController(OrderService orderService, CityService cityService,
                           WaypointService waypointService, CargoService cargoService,
                           TruckService truckService, DriverService driverService) {
        this.orderService = orderService;
        this.cityService = cityService;
        this.waypointService = waypointService;
        this.truckService = truckService;
        this.driverService = driverService;
        this.cargoService = cargoService;
    }

    //TODO: sessionStatus.setComplete() in the end

    /**
     * 1st step - add loads
     *
     * @param step1     is now step1?
     * @param step2     is now step2?
     * @param step3     is now step3?
     * @param page      page number of cargo list
     * @param cargoList all added loads
     */
    @GetMapping("/add")
    public String showAddingOrder(@RequestParam(defaultValue = "1") int page,
                                  @ModelAttribute("step1") boolean step1,
                                  @ModelAttribute("step2") boolean step2,
                                  @ModelAttribute("step3") boolean step3,
                                  @ModelAttribute("cargoList") List<CargoDTO> cargoList,
                                  @ModelAttribute("order") OrderDTO order,
                                  @ModelAttribute("routeDistance") long routeDistance,
                                  Model model) {
        if (step1) {
            model.addAttribute("cargoListOnOnePage", cargoService.getCargosOnPageNumber(page, cargoList));
            model.addAttribute("currentPage", page);
            model.addAttribute("numberOfPages", cargoService.getNumberOfPages(cargoList));
        } else if (step2) {
            model.addAttribute("trucksList", truckService.getAppropriateTrucks(cargoList));
        } else if (step3) {
            model.addAttribute("drivers", driverService.getAppropriateDrivers(order.getTruck(), routeDistance));
            model.addAttribute("numberOfDrivers", truckService.getNumberOfDrivers(order.getTruck()));
        } else {
            model.addAttribute("truck", truckService.getTruckByRegNumber(order.getTruck()));
            model.addAttribute("drivers", driverService.getDriversByTruckRegNumber(order.getTruck()));
            model.addAttribute("waypoints", waypointService.getWaypointsByOrderID(order.getId()));
            model.addAttribute("loads", cargoService.getLoadsByOrderID(order.getId()));
            model.addAttribute("orderID", order.getId());
        }
        return "orders/currentOrder";
    }

    /**
     * Add cargo to cargo list
     *
     * @param cargoDTO  cargo
     * @param cargoList list of loads
     */
    @PostMapping("/add/cargoList/add")
    @ResponseStatus(HttpStatus.OK)
    public void addCargo(@Valid @RequestBody CargoDTO cargoDTO,
                         @ModelAttribute("cargoList") List<CargoDTO> cargoList) {
        cargoList.add(cargoDTO);
    }

    /**
     * Delete cargo from cargo list by id (index in list)
     *
     * @param cargoList list of loads
     * @param id        index of cargo in list
     */
    @PostMapping("/add/cargoList/delete")
    public RedirectView deleteCargo(@RequestParam("id") int id,
                                    @ModelAttribute("cargoList") List<CargoDTO> cargoList) {
        cargoList.remove(id);
        return getRedirectViewToMainAddPage();
    }

    /**
     * Edit cargo in cargo list by id
     *
     * @param cargoDTO  edited cargo with new fields, field id set to index in list
     * @param cargoList list of loads
     */
    @PostMapping("/add/cargoList/edit")
    @ResponseStatus(HttpStatus.OK)
    public void editCargo(@Valid @RequestBody CargoDTO cargoDTO,
                          @ModelAttribute("cargoList") List<CargoDTO> cargoList) {
        int id = cargoDTO.getId().intValue();
        cargoDTO.setId(null);
        cargoList.set(id, cargoDTO);
    }

    /**
     * Get redirect to main adding order page without any parameters
     *
     * @return redirect view to main adding order page
     */
    private RedirectView getRedirectViewToMainAddPage() {
        RedirectView view = new RedirectView("/orders/add");
        view.setContextRelative(true);
        view.setExposeModelAttributes(false);
        return view;
    }

    /**
     * Go to step2
     */
    @PostMapping("/add/step2")
    public RedirectView goToStep2(Model model) {
        model.addAttribute("step1", false);
        model.addAttribute("step2", true);
        return getRedirectViewToMainAddPage();
    }

    /**
     * Build route
     * Save loads
     * Create and save waypoints
     * Go to step3
     */
    @PostMapping("/add/step3")
    public RedirectView goToStep3(@RequestParam("truckRegNumber") String truckRegNumber,
                                  @ModelAttribute("order") OrderDTO order,
                                  @ModelAttribute("cargoList") List<CargoDTO> cargoList,
                                  Model model) {
        order.setTruck(truckRegNumber);
        orderService.updateOrderTruck(order);
        long routeDistance = waypointService.buildRoute(order, cargoList);
        model.addAttribute("routeDistance", routeDistance);
        model.addAttribute("step2", false);
        model.addAttribute("step3", true);
        return getRedirectViewToMainAddPage();
    }

    @PostMapping("/add/finishOrder")
    public RedirectView finishOrder(@RequestParam("driverID") List<Integer> driverIDs,
                                    @ModelAttribute("order") OrderDTO order,
                                    Model model) {
        try {
            driverService.updateDriversTruck(driverIDs, order.getTruck());
            model.addAttribute("step3", false);
        } catch (RuntimeException e) {

        }
        return getRedirectViewToMainAddPage();
    }

    @GetMapping("/waypointsCitiesLocations")
    @ResponseBody
    public List<LocationDTO> getWaypointsCitiesLocations(@RequestParam("orderID") Long orderID) {
        return waypointService.getCitiesCoordinatesByOrderID(orderID);
    }

    @GetMapping()
    public String show(@RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("orders", orderService.getOrdersOnPageNumber(page));
        model.addAttribute("numberOfPages", orderService.getNumberOfPages());
        model.addAttribute("currentPage", page);
        return "orders/order";
    }

    @GetMapping("/more")
    public String showMore(@RequestParam Long orderID, Model model) {
        String orderTruckRegNumber = orderService.getTruckRegNumber(orderID);
        model.addAttribute("truck", truckService.getTruckByRegNumber(orderTruckRegNumber));
        model.addAttribute("drivers", driverService.getDriversByTruckRegNumber(orderTruckRegNumber));
        model.addAttribute("waypoints", waypointService.getWaypointsByOrderID(orderID));
        model.addAttribute("loads", cargoService.getLoadsByOrderID(orderID));
        model.addAttribute("orderID", orderID);
        return "orders/orderMore";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long orderID) {
        orderService.deleteOrder(orderID);
        return "redirect:/orders";
    }

    @ModelAttribute("order")
    public OrderDTO getOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setComplete(false);
        Long id = orderService.addOrder(orderDTO);
        orderDTO.setId(id);
        return orderDTO;
    }

    @ModelAttribute("cargoList")
    public List<CargoDTO> getCargoList() {
        return new ArrayList<>();
    }

    @ModelAttribute("citiesList")
    public List<String> getCitiesList() {
        return cityService.getAllCities();
    }

    @ModelAttribute("step1")
    public Boolean getStep1() {
        return true;
    }

    @ModelAttribute("step2")
    public Boolean getStep2() {
        return false;
    }

    @ModelAttribute("step3")
    public Boolean getStep3() {
        return false;
    }

    @ModelAttribute("routeDistance")
    public Long getRouteDistance() {
        return -1L;
    }
}
