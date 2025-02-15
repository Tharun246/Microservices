package com.example.Inventory_Service.Service;

import com.example.Inventory_Service.Dto.InvResponse;
import com.example.Inventory_Service.Model.Inventory;
import com.example.Inventory_Service.Repository.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService
{
    @Autowired
    private InventoryRepo inventoryRepo;

    @Transactional(readOnly = true)
    public List<InvResponse> isInStockInList(List<String> skuCodes)
    {
        return inventoryRepo.findBySkuCodeIn(skuCodes).stream().map(this::toInvresponse).toList();
    }

    public InvResponse toInvresponse(Inventory inventory)
    {
        InvResponse inv=new InvResponse();
        inv.setSkuCode(inventory.getSkuCode());
        inv.setInStock(inventory.getQuantity()>0);
        return inv;
    }


}
