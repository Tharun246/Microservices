package com.example.Inventory_Service.Controller;

import com.example.Inventory_Service.Repository.InventoryRepo;
import com.example.Inventory_Service.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/inventory")
public class InventoryController
{
    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable(name = "sku-code") String skuCode)
    {
        return inventoryService.isInStock(skuCode);
    }

}
