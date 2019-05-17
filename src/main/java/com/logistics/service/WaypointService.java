package com.logistics.service;

import com.google.ortools.constraintsolver.*;
import com.logistics.dao.interfaces.CargoDAO;
import com.logistics.dao.interfaces.WaypointDAO;
import com.logistics.dao.neo4j.MapDAO;
import com.logistics.dto.CargoDTO;
import com.logistics.dto.WaypointDTO;
import com.logistics.entity.waypoint.Waypoint;
import com.logistics.dao.interfaces.CityDAO;
import com.logistics.dao.interfaces.OrderDAO;
import com.logistics.dto.LocationDTO;
import com.logistics.dto.OrderDTO;
import com.logistics.entity.user.UserRole;
import com.logistics.entity.waypoint.WaypointType;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.*;

import java.util.logging.Logger;

@Service
public class WaypointService {
    /**
     * Load library for google or-tools
     */
    static {
        System.loadLibrary("jniortools");
    }

    private static final Logger logger = Logger.getLogger(WaypointService.class.getName());

    /**
     * Data model for TSP (Traveling Salesman Problem)
     */
    private class DataModel {
        /**
         * Matrix of distances between cities
         */
        final long[][] distanceMatrix;

        /**
         * Number of vehicles for delivery
         */
        final int vehicleNumber = 1;

        /**
         * Starting point for route
         * It is index in citiesList
         */
        final int depot = 0;

        /**
         * List of cities with postfix number
         * Index to city name
         */
        final List<String> citiesList;

        /**
         * Map of cities with postfix number
         * City name to index in citiesList
         */
        final Map<String, Integer> citiesMap;

        /**
         * Pickups and deliveries requests
         * One request is pair of indexes
         * Index is index of city in citiesList
         */
        final int[][] pickupsDeliveries;

        /**
         * Demand - weight of cargo for delivery
         * Positive weight for loading cities
         * Negative weight for unloading cities
         */
        final long[] demands;

        /**
         * Max weight capacity of vehicle (truck)
         */
        final long[] vehicleCapacities;

        /**
         * Loading city with postfix number to cargo
         */
        private Map<String, CargoDTO> loadCityToCargoMap = new HashMap<>();

        /**
         * Unloading city with postfix number to cargo
         */
        private Map<String, CargoDTO> unloadCityToCargoMap = new HashMap<>();

        /**
         * Pairs of loading and unloading cities with postfix numbers
         */
        private Map<String, String> loadUnloadCitiesMap = new HashMap<>();

        DataModel(List<CargoDTO> cargoList, OrderDTO order) {
            vehicleCapacities = new long[] {truckService.getWeightCapacityByRegNumber(order.getTruck())};
            Set<String> citiesSet = createCitiesSet(cargoList);
            citiesList = new ArrayList<>(citiesSet);
            //add starting point for route
            citiesList.add(0, cargoList.get(0).getLoadingCity() + "_depot");
            citiesMap = createCitiesMap(citiesList);
            demands = createDemands(cargoList);
            pickupsDeliveries = createPickupsAndDeliveries();
            distanceMatrix = calculateDistances();
        }

        /**
         * Creating set of cities with postfix numbers
         * e.g. Moscow -> Moscow_1
         * Also creating two maps which contains pairs
         * of new city names (with postfix number) and loads for this cities
         * Creating map with pairs of loading and unloading new city names
         * @param cargoList list of all loads
         * @return set of cities with postfix numbers
         */
        private Set<String> createCitiesSet(List<CargoDTO> cargoList) {
            Map<String, Integer> citiesFrequency = new HashMap<>();
            for (CargoDTO cargoDTO : cargoList) {
                String loadCityName = addCityToFreqMap(citiesFrequency, cargoDTO.getLoadingCity());
                loadCityToCargoMap.put(loadCityName, cargoDTO);
                String unloadCityName = addCityToFreqMap(citiesFrequency, cargoDTO.getUnloadingCity());
                unloadCityToCargoMap.put(unloadCityName, cargoDTO);
                loadUnloadCitiesMap.put(loadCityName, unloadCityName);
            }
            return citiesFrequency.keySet();
        }

        private String addCityToFreqMap(Map<String, Integer> citiesFrequency, String city) {
            String firstCity = city + "_1";
            if (!citiesFrequency.containsKey(firstCity)) {
                citiesFrequency.put(firstCity, 1);
                return firstCity;
            } else {
                int frequency = citiesFrequency.get(firstCity) + 1;
                citiesFrequency.put(firstCity, frequency);
                String nCity = city + "_" + frequency;
                citiesFrequency.put(nCity, 1);
                return nCity;
            }
        }

        /**
         * Calculating distances between cities and adding them to matrix
         * @return matrix of distances
         */
        private long[][] calculateDistances() {
            long[][] distanceMatrix = new long[citiesList.size()][citiesList.size()];
            for (int i = 0; i < citiesList.size(); i++) {
                distanceMatrix[i][i] = 0;
                for (int j = i + 1; j < citiesList.size(); j++) {
                    String[] iCityTokens = citiesList.get(i).split("_");
                    String[] jCityTokens = citiesList.get(j).split("_");
                    int dist = 0;
                    if (!iCityTokens[0].equals(jCityTokens[0])) {
                        dist = mapDAO.getDistanceBetweenCities(iCityTokens[0], jCityTokens[0]);
                    }
                    distanceMatrix[i][j] = dist;
                    distanceMatrix[j][i] = dist;
                }
            }
            return distanceMatrix;
        }

        /**
         * Creating map of city name with prefix number to index in citiesList
         */
        private Map<String, Integer> createCitiesMap(List<String> citiesList) {
            Map<String, Integer> citiesMap = new HashMap<>();
            for (int i = 0; i < citiesList.size(); i++) {
                citiesMap.put(citiesList.get(i), i);
            }
            return citiesMap;
        }

        /**
         * Creating pickups and deliveries requests
         */
        private int[][] createPickupsAndDeliveries() {
            int[][] pickupsDeliveries = new int[loadUnloadCitiesMap.size()][];
            int i = 0;
            for (Map.Entry<String, String> loadUnloadPair : loadUnloadCitiesMap.entrySet()) {
                pickupsDeliveries[i] = new int[]{citiesMap.get(loadUnloadPair.getKey()),
                        citiesMap.get(loadUnloadPair.getValue())};
                i++;
            }
            return pickupsDeliveries;
        }

        /**
         * Creating demands for weight capacity
         */
        private long[] createDemands(List<CargoDTO> cargoList) {
            long[] demands = new long[citiesList.size()];
            demands[0] = 0;
            for (int i = 1; i < citiesList.size(); i++) {
                String cityName = citiesList.get(i);
                if (loadCityToCargoMap.containsKey(cityName)) {
                    demands[i] = loadCityToCargoMap.get(cityName).getWeight();
                } else {
                    demands[i] = -unloadCityToCargoMap.get(cityName).getWeight();
                }
            }
            return demands;
        }
    }

    private CargoDAO cargoDAO;
    private WaypointDAO waypointDAO;
    private OrderDAO orderDAO;
    private CityDAO cityDAO;
    private CargoService cargoService;
    private TruckService truckService;
    private MapDAO mapDAO;
    private ModelMapper modelMapper;

    @Autowired
    public WaypointService(CargoDAO cargoDAO,
                           WaypointDAO waypointDAO, OrderDAO orderDAO,
                           CityDAO cityDAO, CargoService cargoService,
                           TruckService truckService,
                           MapDAO mapDAO, ModelMapper modelMapper) {
        this.cargoDAO = cargoDAO;
        this.waypointDAO = waypointDAO;
        this.orderDAO = orderDAO;
        this.cityDAO = cityDAO;
        this.cargoService = cargoService;
        this.truckService = truckService;
        this.mapDAO = mapDAO;
        this.modelMapper = modelMapper;
        modelMapperSettings();
    }

    private void modelMapperSettings() {
        TypeMap<Waypoint, WaypointDTO> typeMap = modelMapper.createTypeMap(Waypoint.class, WaypointDTO.class);
        typeMap.addMapping(src -> src.getCity().getName(), WaypointDTO::setCity);
        typeMap.addMapping(src -> src.getCargo().getName(), WaypointDTO::setCargoName);
    }

    /**
     * Build route with help of google or-tools
     */
    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public long buildRoute(OrderDTO order, List<CargoDTO> cargoList) {
        // Instantiate the data problem.
        final DataModel data = new DataModel(cargoList, order);

        // Create Routing Index Manager
        RoutingIndexManager manager =
                new RoutingIndexManager(data.distanceMatrix.length, data.vehicleNumber, data.depot);

        // Create Routing Model.
        RoutingModel routing = new RoutingModel(manager);

        // Create and register a transit callback.
        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    // Convert from routing variable Index to user NodeIndex.
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return data.distanceMatrix[fromNode][toNode];
                });

        // Define cost of each arc.
        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        // Add Distance constraint.
        routing.addDimension(transitCallbackIndex, // transit callback index
                0, // no slack
                Integer.MAX_VALUE, // vehicle maximum travel distance
                true, // start cumul to zero
                "Distance");
        RoutingDimension distanceDimension = routing.getMutableDimension("Distance");
        distanceDimension.setGlobalSpanCostCoefficient(Integer.MAX_VALUE);

        // Define Transportation Requests.
        Solver solver = routing.solver();
        for (int[] request : data.pickupsDeliveries) {
            long pickupIndex = manager.nodeToIndex(request[0]);
            long deliveryIndex = manager.nodeToIndex(request[1]);
            routing.addPickupAndDelivery(pickupIndex, deliveryIndex);
            solver.addConstraint(
                    solver.makeEquality(routing.vehicleVar(pickupIndex), routing.vehicleVar(deliveryIndex)));
            solver.addConstraint(solver.makeLessOrEqual(
                    distanceDimension.cumulVar(pickupIndex), distanceDimension.cumulVar(deliveryIndex)));
        }

        // Add Capacity constraint.
        final int demandCallbackIndex = routing.registerUnaryTransitCallback((long fromIndex) -> {
            // Convert from routing variable Index to user NodeIndex.
            int fromNode = manager.indexToNode(fromIndex);
            return data.demands[fromNode];
        });
        routing.addDimensionWithVehicleCapacity(demandCallbackIndex, 0, // null capacity slack
                data.vehicleCapacities, // vehicle maximum capacities
                true, // start cumul to zero
                "Capacity");

        // Setting first solution heuristic.
        RoutingSearchParameters searchParameters =
                main.defaultRoutingSearchParameters()
                        .toBuilder()
                        .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PARALLEL_CHEAPEST_INSERTION)
                        .build();

        // Solve the problem.
        Assignment solution = routing.solveWithParameters(searchParameters);


        // Print solution on console.
        printSolution(data, routing, manager, solution);

        return saveWaypointsAndLoads(order.getId(), data, routing, manager, solution);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public long saveWaypointsAndLoads(Long orderID,
                                      DataModel data, RoutingModel routing,
                                      RoutingIndexManager manager, Assignment solution) {
        long routeDistance = 0;
        long index = routing.start(0);
        int waypointNumber = 0;
        while (!routing.isEnd(index)) {
            if (waypointNumber == 0) {
                waypointNumber++;
                index = solution.value(routing.nextVar(index));
                continue;
            }
            long nodeIndex = manager.indexToNode(index);
            Waypoint waypoint = new Waypoint();
            waypoint.setWaypointNumber(waypointNumber++);
            String cityNameWithPostfix = data.citiesList.get((int) nodeIndex);
            String cityName = cityNameWithPostfix.split("_")[0];
            if (data.loadCityToCargoMap.containsKey(cityNameWithPostfix)) {
                waypoint.setWaypointType(WaypointType.LOADING);
                CargoDTO cargoDTO = data.loadCityToCargoMap.get(cityNameWithPostfix);
                long id = cargoService.saveCargo(cargoDTO);
                cargoDTO.setId(id);
                waypoint.setCargo(cargoDAO.read(id));
            } else {
                waypoint.setWaypointType(WaypointType.UNLOADING);
                CargoDTO cargoDTO = data.unloadCityToCargoMap.get(cityNameWithPostfix);
                waypoint.setCargo(cargoDAO.read(cargoDTO.getId()));
            }
            waypoint.setOrder(orderDAO.read(orderID));
            waypoint.setCity(cityDAO.readByName(cityName));
            waypointDAO.create(waypoint);
            long previousIndex = index;
            index = solution.value(routing.nextVar(index));
            routeDistance += routing.getArcCostForVehicle(previousIndex, index, 0);
        }
        return routeDistance;
    }

    /**
     * Print the solution
     */
    public void printSolution(DataModel data, RoutingModel routing,
                              RoutingIndexManager manager, Assignment solution) {
        long totalDistance = 0;
        long totalLoad = 0;
        for (int i = 0; i < data.vehicleNumber; ++i) {
            long index = routing.start(i);
            logger.info("Route for Vehicle " + i + ":");
            long routeDistance = 0;
            long routeLoad = 0;
            String route = "";
            String cityRoute = "";
            while (!routing.isEnd(index)) {
                long nodeIndex = manager.indexToNode(index);
                routeLoad += data.demands[(int) nodeIndex];
                route += nodeIndex + " Load(" + routeLoad + ") -> ";
                cityRoute += data.citiesList.get((int) nodeIndex) + " Load(" + routeLoad + ") -> ";
                long previousIndex = index;
                index = solution.value(routing.nextVar(index));
                routeDistance += routing.getArcCostForVehicle(previousIndex, index, i);
            }
            logger.info(route + manager.indexToNode(index));
            logger.info(cityRoute + data.citiesList.get(manager.indexToNode(index)));
            logger.info("Distance of the route: " + routeDistance + "m");
            totalDistance += routeDistance;
            totalLoad += routeLoad;
        }
        logger.info("Total Distance of all routes: " + totalDistance + "m");
        logger.info("Total load of all routes: " + totalLoad);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public List<WaypointDTO> getWaypointsByOrderID(Long orderID) {
        List<Waypoint> waypointList = waypointDAO.readByOrderID(orderID);
        Type targetListType = new TypeToken<List<WaypointDTO>>() {}.getType();
        return modelMapper.map(waypointList, targetListType);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public List<LocationDTO> getCitiesCoordinatesByOrderID(Long orderID) {
        List<String> citiesNames = waypointDAO.readCitiesNamesByOrderID(orderID);
        List<LocationDTO> locations = new ArrayList<>();
        for (String cityName : citiesNames) {
            double[] coordinates = mapDAO.getCityCoordinates(cityName);
            locations.add(new LocationDTO(coordinates[0], coordinates[1]));
        }
        return locations;
    }
}
