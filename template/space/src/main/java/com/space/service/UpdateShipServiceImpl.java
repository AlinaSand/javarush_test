package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;

@Service
public class UpdateShipServiceImpl implements UpdateShipService {
    @Autowired
    ShipRepository shipRepository;

    @Override
    public Ship updateShip(Long id, Ship ship) {
        return shipRepository.findById(id).map(createShip ->
            shipRepository.saveAndFlush(convertShip(ship, createShip))).orElse(null);
    }

    public Ship convertShip(Ship ship, Ship createShip){
        if(validateCrewSize(ship) || validatePlanet(ship) || validateName(ship) || validateDate(ship)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (ship.name != null) {
            createShip.name = ship.name;
        }

        if (ship.planet != null) {
            createShip.planet = ship.planet;
        }

        if (ship.crewSize != null) {
            createShip.crewSize = ship.crewSize;
        }

        if (ship.prodDate != null) {
            createShip.prodDate = ship.prodDate;
        }

        if (ship.shipType != null) {
            createShip.shipType = ship.shipType;
        }

        if (ship.speed != null) {
            createShip.speed = ship.speed;
        }

        createShip.isUsed = ship.isUsed == null ? false : ship.isUsed;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(createShip.prodDate.toInstant().toEpochMilli());
        double result = 80 * createShip.speed * (createShip.isUsed ? 0.5 : 1) / (3019 - calendar.get(Calendar.YEAR) + 1);
        createShip.rating = Math.round(result * 100.0) / 100.0;

        return createShip;
    }

    public boolean validateCrewSize(Ship ship){
        if (ship.crewSize == null)
            return false;
        return ship.crewSize > 9999 || ship.crewSize < 1;
    }

    public boolean validatePlanet(Ship ship){
        if(ship.planet == null)
            return false;
        return ship.planet.isEmpty() || ship.planet.length() > 50;
    }

    public boolean validateName(Ship ship){
        if(ship.name == null)
            return false;
        return ship.name.length() > 50 || ship.name.isEmpty() || ship.name.isEmpty();
    }

    public boolean validateDate(Ship ship){
        if(ship.prodDate == null)
            return false;
        return ship.prodDate.getTime() < 0;
    }

    public boolean anyFieldsIsNull(Ship ship) {
        return ship.name == null || ship.planet == null || ship.crewSize == null || ship.prodDate == null ||
                ship.shipType == null || ship.speed == null;
    }
}
