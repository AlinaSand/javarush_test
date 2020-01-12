package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.CreateShipService;
import com.space.service.DeleteShipService;
import com.space.service.ShipService;
import com.space.service.UpdateShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("rest")
public class ShipController {

    @Autowired
    private ShipService shipService;

    @Autowired
    private CreateShipService createShipService;

    @Autowired
    private DeleteShipService deleteShipService;

    @Autowired
    private UpdateShipService updateShipService;

    @PostMapping("/ships")
    public Ship createShip(@RequestBody Ship ship) {
        return createShipService.createShip(ship);
    }

    @PostMapping("/ships/{id}")
    public Ship updateShip(@PathVariable Long id, @RequestBody Ship ship){
        if(id < 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if(id > 1 && shipService.shipFieldsIsNull(ship)){
            return shipService.getById(id).orElse(null);
        }

//        if(!shipService.getById(id).isPresent())
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Ship result = updateShipService.updateShip(id, ship);

        if(result == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return result;
    }

    @GetMapping("/ships/count")
    public Integer getCountShips(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String planet,
                                 @RequestParam(required = false) ShipType shipType,
                                 @RequestParam(required = false) Long after,
                                 @RequestParam(required = false) Long before,
                                 @RequestParam(required = false) Boolean isUsed,
                                 @RequestParam(required = false) Double minSpeed,
                                 @RequestParam(required = false) Double maxSpeed,
                                 @RequestParam(required = false) Integer minCrewSize,
                                 @RequestParam(required = false) Integer maxCrewSize,
                                 @RequestParam(required = false) Double minRating,
                                 @RequestParam(required = false) Double maxRating) {

        return getShips(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize,
                maxCrewSize, minRating, maxRating, null, 0, Integer.MAX_VALUE).size();
    }

    @GetMapping("/ships/{id}")
    public Ship getShip(@PathVariable Long id){

        if(id < 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if(!shipService.getById(id).isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return shipService.getById(id).get();
    }

    @DeleteMapping("/ships/{id}")
    public ResponseEntity deleteShip(@PathVariable Long id){
        if(id < 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if(!shipService.getById(id).isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        deleteShipService.deleteShip(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/ships")
    public List<Ship> getShips(@RequestParam(required = false) String name,
                               @RequestParam(required = false) String planet,
                               @RequestParam(required = false) ShipType shipType,
                               @RequestParam(required = false) Long after,
                               @RequestParam(required = false) Long before,
                               @RequestParam(required = false) Boolean isUsed,
                               @RequestParam(required = false) Double minSpeed,
                               @RequestParam(required = false) Double maxSpeed,
                               @RequestParam(required = false) Integer minCrewSize,
                               @RequestParam(required = false) Integer maxCrewSize,
                               @RequestParam(required = false) Double minRating,
                               @RequestParam(required = false) Double maxRating,
                               @RequestParam(required = false) ShipOrder order,
                               @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                               @RequestParam(required = false, defaultValue = "3") Integer pageSize){
        if (order == null)
            order = ShipOrder.ID;
        return shipService.getShipsByPage(pageNumber, pageSize,
                    shipService.getShipsByMinRating(minRating,
                        shipService.getShipsByMaxRating(maxRating,
                            shipService.getShipsByShipOrder(order,
                                shipService.getShipsByUsed(isUsed,
                                    shipService.getShipsByMaxCrewSize(maxCrewSize,
                                        shipService.getShipsByMinCrewSize(minCrewSize,
                                            shipService.getShipsByMinSpeed(minSpeed,
                                                shipService.getShipsByMaxSpeed(maxSpeed,
                                                    shipService.getShipsByShipType(shipType,
                                                        shipService.getShipsByAfter(after,
                                                            shipService.getShipsByBefore(before,
                                                                shipService.getShipsByName(name,
                                                                    shipService.getShipsByPlanet(planet,
                                                                        shipService.getAllShips()))))))))))))));
    }
}