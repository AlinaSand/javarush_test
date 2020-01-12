package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipRepository shipRepository;

    @Override
    public Optional<Ship> getById(Long id) {
        return shipRepository.findById(id);
    }

    @Override
    public boolean shipFieldsIsNull(Ship ship) {
        return ship.planet == null && ship.speed == null && ship.prodDate == null &&
                ship.name == null && ship.shipType == null && ship.crewSize == null;
    }

    @Override
    public List<Ship> getAllShips() {
        return shipRepository.findAll();
    }

    @Override
    public List<Ship> getShipsByPage(Integer pageNumber, Integer pageSize, List<Ship> ships) {
        List<Ship> result = new ArrayList<>();
        int num = pageNumber * pageSize;
        for (int i = num; i < Math.min(num + pageSize, ships.size()); i++){
            result.add(ships.get(i));
        }
        return result;
    }

    @Override
    public List<Ship> getShipsByMinRating(Double minRating, List<Ship> ships) {
        if(minRating == null)
            return ships;
        List<Ship> result = new ArrayList<>();
        for (int i = 0; i < ships.size(); i++){
            if(ships.get(i).rating >= minRating){
                result.add(ships.get(i));
            }
        }
        return result;
    }

    @Override
    public List<Ship> getShipsByMaxRating(Double maxRating, List<Ship> ships) {
        if(maxRating == null)
            return ships;
        List<Ship> result = new ArrayList<>();
        for (int i = 0; i < ships.size(); i++){
            if(ships.get(i).rating <= maxRating){
                result.add(ships.get(i));
            }
        }
        return result;
    }

    @Override
    public List<Ship> getShipsByShipOrder(ShipOrder order, List<Ship> ships) {
        if(order != null){
            switch (order){
                case SPEED:
                    ships.sort((o1, o2) -> {
                    if(o1.speed > o2.speed){
                        return 1;
                    } else if(o1.speed.equals(o2.speed)){
                        return 0;
                    } else{
                        return -1;
                    }
                    });
                    break;
                case ID:
                    ships.sort((o1, o2) -> (int) (o1.id - o2.id));
                    break;
                case DATE:
                    ships.sort(Comparator.comparing(o -> o.prodDate));
                    break;
                case RATING:
                    ships.sort((o1, o2) -> (int) (o1.rating - o2.rating));
                    break;
            }
        }
        return ships;
    }

    @Override
    public List<Ship> getShipsByUsed(Boolean isUsed, List<Ship> ships) {
        if(isUsed == null)
            return ships;
        List<Ship> result = new ArrayList<>();
        for(Ship ship : ships){
            if(ship.isUsed == isUsed){
                result.add(ship);
            }
        }
        return result;
    }

    @Override
    public List<Ship> getShipsByMaxCrewSize(Integer maxCrewSize, List<Ship> ships) {
        if(maxCrewSize == null)
            return ships;
        List<Ship> result = new ArrayList<>();
        for(Ship ship : ships){
            if(ship.crewSize <= maxCrewSize){
                result.add(ship);
            }
        }
        return result;
    }

    @Override
    public List<Ship> getShipsByMinCrewSize(Integer minCrewSize, List<Ship> ships) {
        if(minCrewSize == null)
            return ships;
        List<Ship> result = new ArrayList<>();
        for(Ship ship : ships){
            if(ship.crewSize >= minCrewSize){
                result.add(ship);
            }
        }
        return result;
    }

    @Override
    public List<Ship> getShipsByMinSpeed(Double minSpeed, List<Ship> ships) {
        if(minSpeed == null)
            return ships;
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships){
            if(ship.speed >= minSpeed){
                result.add(ship);
            }
        }
        return result;
    }

    @Override
    public List<Ship> getShipsByMaxSpeed(Double maxSpeed, List<Ship> ships) {
        if(maxSpeed == null)
            return ships;
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships){
            if(ship.speed <= maxSpeed){
                result.add(ship);
            }
        }
        return result;
    }

    @Override
    public List<Ship> getShipsByAfter(Long after, List<Ship> ships) {
        if(after == null)
            return ships;
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships){
            if(ship.prodDate.getTime() > after){
                result.add(ship);
            }
        }
        return result;
    }

    @Override
    public List<Ship> getShipsByBefore(Long before, List<Ship> ships) {
        if(before == null)
            return ships;
        Date before1 = new Date(before);
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships){
            if (ship.prodDate != null) {
                Long time = ship.prodDate.getTime();
                if(time < before){
                    result.add(ship);
                }
            }
//                if (ship.prodDate.before(before1)) {
//                    result.add(ship);
//                }
//            }
        }
//        return result;
//        return ships.stream()
//                .filter(ship -> before == null || ship.prodDate.toInstant().toEpochMilli() <= before)
//                .collect(Collectors.toList());
        return ships.stream().filter(ship -> ship.prodDate.before(new Date(before))).collect(Collectors.toList());
    }

    @Override
    public List<Ship> getShipsByShipType(ShipType shipType, List<Ship> ships) {
        if(shipType == null)
            return ships;
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships){
            if(ship.shipType == shipType){
                result.add(ship);
            }
        }
        return result;
//        return ships.stream()
//                .filter(ship -> shipType == null || ship.shipType == shipType)
//                .collect(Collectors.toList());
//        return ships;
    }

    @Override
    public List<Ship> getShipsByName(String name, List<Ship> ships) {
        if (name == null)
            return ships;
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships){
            if(ship.name.contains(name)){
                result.add(ship);
            }
        }
        return result;
    }

    @Override
    public List<Ship> getShipsByPlanet(String planet, List<Ship> ships) {
        if(planet == null)
            return ships;
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships){
            if(ship.planet.contains(planet)){
                result.add(ship);
            }
        }
        return result;
    }
}
