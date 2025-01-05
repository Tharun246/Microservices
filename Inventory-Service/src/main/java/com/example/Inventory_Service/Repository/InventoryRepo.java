package com.example.Inventory_Service.Repository;

import com.example.Inventory_Service.Model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory,Long>
{

    Optional<Inventory> findBySkuCode(String skuCode);
}
