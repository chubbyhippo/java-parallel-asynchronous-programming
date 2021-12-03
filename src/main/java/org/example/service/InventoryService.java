package org.example.service;

import org.example.domain.Inventory;

import static org.example.util.CommonUtil.delay;

public class InventoryService {
    public Inventory retrieveInventory() {
        delay(500);
        return Inventory.builder()
                .count(2).build();

    }
}
