package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Calendar;

@Service
public class CreateShipServiceImpl implements CreateShipService {
    @Autowired
    private ShipRepository shipRepository;

    @Override
    public Ship createShip(Ship ship) {
        if(validateCrewSize(ship) || validatePlanet(ship) || validateName(ship) || validateDate(ship) || anyFieldsIsNull(ship)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Ship createShip = new Ship();
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

        if (ship.prodDate != null && ship.speed != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(ship.prodDate.toInstant().toEpochMilli());
            double result = 80 * ship.speed * (createShip.isUsed ? 0.5 : 1) / (3019 - calendar.get(Calendar.YEAR) + 1);
            createShip.rating = Math.round(result * 100.0) / 100.0;
        }
        return shipRepository.save(createShip);
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
