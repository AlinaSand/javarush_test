package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;

import java.util.List;
import java.util.Optional;

public interface ShipService {
    List<Ship> getAllShips();

    boolean shipFieldsIsNull(Ship ship);

    Optional<Ship> getById(Long id);

    List<Ship> getShipsByPage(Integer pageNumber, Integer pageSize, List<Ship> ships);

    List<Ship> getShipsByMinRating(Double minRating, List<Ship> ships);

    List<Ship> getShipsByMaxRating(Double maxRating, List<Ship> ships);

    List<Ship> getShipsByShipOrder(ShipOrder order, List<Ship> ships);

    List<Ship> getShipsByUsed(Boolean isUsed, List<Ship> ships);

    List<Ship> getShipsByMaxCrewSize(Integer maxCrewSize, List<Ship> ships);

    List<Ship> getShipsByMinCrewSize(Integer minCrewSize, List<Ship> ships);

    List<Ship> getShipsByMinSpeed(Double minSpeed, List<Ship> ships);

    List<Ship> getShipsByMaxSpeed(Double maxSpeed, List<Ship> ships);

    List<Ship> getShipsByAfter(Long after, List<Ship> ships);

    List<Ship> getShipsByBefore(Long before, List<Ship> ships);

    List<Ship> getShipsByShipType(ShipType shipType, List<Ship> ships);

    List<Ship> getShipsByName(String name, List<Ship> ships);

    List<Ship> getShipsByPlanet(String planet, List<Ship> ships);

}
