package com.space.service;

import com.space.model.Ship;

public interface UpdateShipService {
    Ship updateShip(Long id, Ship ship);
}
