package com.example.Inventory_Service.Controller;

import com.example.Inventory_Service.Dto.InvResponse;
import com.example.Inventory_Service.Repository.InventoryRepo;
import com.example.Inventory_Service.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/inventory")
public class InventoryController
{
    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InvResponse> isInStock(@RequestParam List<String> skuCodes)
    {
        return inventoryService.isInStockInList(skuCodes);
    }

}
