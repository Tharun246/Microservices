package com.example.Inventory_Service.Service;

import com.example.Inventory_Service.Repository.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService
{
    @Autowired
    private InventoryRepo inventoryRepo;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode)
    {
        return inventoryRepo.findBySkuCode(skuCode).isPresent();
    }
}
