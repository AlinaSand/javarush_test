package com.space.service;

import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteShipServiceImpl implements DeleteShipService {
    @Autowired
    private ShipRepository shipRepository;

    @Override
    public void deleteShip(Long id) {
        shipRepository.deleteById(id);
    }
}
